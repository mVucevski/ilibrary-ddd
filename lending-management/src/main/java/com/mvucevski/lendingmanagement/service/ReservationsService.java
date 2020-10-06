package com.mvucevski.lendingmanagement.service;

import com.mvucevski.lendingmanagement.domain.*;
import com.mvucevski.lendingmanagement.domain.event.LoanReturned;
import com.mvucevski.lendingmanagement.domain.event.ReservationCreated;
import com.mvucevski.lendingmanagement.exceptions.BookAvailableCopiesException;
import com.mvucevski.lendingmanagement.exceptions.BookNotFoundException;
import com.mvucevski.lendingmanagement.exceptions.ReservationsLoansLimitException;
import com.mvucevski.lendingmanagement.exceptions.UserMembershipException;
import com.mvucevski.lendingmanagement.port.client.BookCatalogClient;
import com.mvucevski.lendingmanagement.repository.loans.LoansRepository;
import com.mvucevski.lendingmanagement.repository.reservations.ReservationsRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mvucevski.lendingmanagement.Constants.*;

@Service
public class ReservationsService {

    private final ReservationsRepository repository;
    private BookCatalogClient bookCatalogClient;
    private LoansRepository loansRepository;
    private RabbitTemplate rabbitTemplate;

    public ReservationsService(@Qualifier("dbReservationsRepository") ReservationsRepository repository,
                               BookCatalogClient bookCatalogClient,
                               LoansRepository loansRepository,
                               RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.bookCatalogClient = bookCatalogClient;
        this.loansRepository = loansRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Reservation> getAllReservations(){
        return repository.getAllReservations();
    }

    public Optional<Reservation> getReservationById(ReservationId id){
        return repository.getReservationById(id);
    }

    public List<Reservation> getAllReservationsByBookId(BookId bookId){
        return repository.getAllReservationsByBookId(bookId);
    }

    public Reservation createReservation(BookId bookId, User user){

        if(user.isMembershipExpired()){
            throw new UserMembershipException("Please start or renew your membership before making reservation!");
        }

        List<Reservation> reservations = repository.findReservationsByUserId(user.getUserId());

        if(reservations.size() >= MAX_RESERVATIONS_PER_USER){
            System.out.println("You have reached the limit of active reservations!");
            throw new ReservationsLoansLimitException("You have reached the limit of active reservations!");
        }

        if(reservations.stream().anyMatch(e->e.getBookId().getId().equals(bookId.getId()))){
            System.out.println("You already have active reservation for this book");
            throw new ReservationsLoansLimitException("You already have active reservation for this book");
        }

        if(loansRepository.findLoanByUserIdAndBookId(user.getUserId(), bookId).isPresent()){
            throw new ReservationsLoansLimitException("You already have active loan for this book");
        }

        int bookCopiesLeft = bookCatalogClient.getAvailableCopiesByBookId(bookId);
        if(bookCopiesLeft==0){
            throw new BookAvailableCopiesException("The Book with ID: '" + bookId.getId() + "' doesn't have available copies at the moment!");
        }else if(bookCopiesLeft<0){
            throw new BookNotFoundException("Book with id: " + bookId.getId() + " doesn't exist!");
        }

        Reservation reservation = repository.saveReservation(new Reservation(bookId, user.getUserId()));

        System.out.println("Reservation ADDED");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, RESERVATION_CREATED_ROUTING_KEY, new ReservationCreated(bookId.getId(), Instant.now()));

        return reservation;
    }

    public boolean removeReservation(ReservationId reservationId, UserId userId){
        Optional<Reservation> reservationOpt = repository.getReservationById(reservationId);

        if(reservationOpt.isPresent()){
            Reservation reservation = reservationOpt.get();

            if(reservation.getUserId().getId().equals(userId.getId())){
                repository.deleteReservation(reservationId);
                System.out.println("Reservation Removed");
                rabbitTemplate.convertAndSend(EXCHANGE_NAME, LOAN_RETURNED_ROUTING_KEY, new LoanReturned(reservation.getBookId().getId(), Instant.now()));
            }else{
                System.out.println("User can't remove reservation of another user");
                throw new BookNotFoundException("User can't remove reservation of another user");
            }
        }
        return true;
    }

    public Reservation saveReservation(Reservation reservation){
        return repository.saveReservation(reservation);
    }
}

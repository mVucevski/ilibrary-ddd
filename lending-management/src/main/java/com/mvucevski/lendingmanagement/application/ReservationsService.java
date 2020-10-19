package com.mvucevski.lendingmanagement.application;

import com.mvucevski.lendingmanagement.domain.event.LoanReturned;
import com.mvucevski.lendingmanagement.domain.event.ReservationCreated;
import com.mvucevski.lendingmanagement.domain.model.*;
import com.mvucevski.lendingmanagement.exceptions.BookAvailableCopiesException;
import com.mvucevski.lendingmanagement.exceptions.BookNotFoundException;
import com.mvucevski.lendingmanagement.exceptions.ReservationsLoansLimitException;
import com.mvucevski.lendingmanagement.exceptions.UserMembershipException;
import com.mvucevski.lendingmanagement.port.client.BookCatalogClient;
import com.mvucevski.lendingmanagement.domain.repository.loans.LoansRepository;
import com.mvucevski.lendingmanagement.domain.repository.reservations.ReservationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.mvucevski.lendingmanagement.Constants.*;

@Service
public class ReservationsService {

    private final ReservationsRepository repository;
    private BookCatalogClient bookCatalogClient;
    private LoansRepository loansRepository;
    private RabbitTemplate rabbitTemplate;
    private Logger logger;

    public ReservationsService(@Qualifier("dbReservationsRepository") ReservationsRepository repository,
                               BookCatalogClient bookCatalogClient,
                               LoansRepository loansRepository,
                               RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.bookCatalogClient = bookCatalogClient;
        this.loansRepository = loansRepository;
        this.rabbitTemplate = rabbitTemplate;
        logger = LoggerFactory.getLogger(ReservationsService.class);
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

        if(user.getIsMembershipExpired()){
            throw new UserMembershipException("Please start or renew your membership before making reservation!");
        }

        List<Reservation> reservations = repository.findReservationsByUserId(user.getId());

        if(reservations.size() >= MAX_RESERVATIONS_PER_USER){
            throw new ReservationsLoansLimitException("You have reached the limit of active reservations!");
        }

        if(reservations.stream().anyMatch(e->e.getBookId().getId().equals(bookId.getId()))){
            throw new ReservationsLoansLimitException("You already have active reservation for this book");
        }

        if(loansRepository.findLoanByUserIdAndBookId(user.getId(), bookId).isPresent()){
            throw new ReservationsLoansLimitException("You already have active loan for this book");
        }

        int bookCopiesLeft = bookCatalogClient.getAvailableCopiesByBookId(bookId);
        if(bookCopiesLeft==0){
            throw new BookAvailableCopiesException("The Book with ID: '" + bookId.getId() + "' doesn't have available copies at the moment!");
        }else if(bookCopiesLeft<0){
            throw new BookNotFoundException("Book with id: " + bookId.getId() + " doesn't exist!");
        }

        Reservation reservation = repository.saveReservation(new Reservation(bookId, user.getId()));

        logger.info("Created reservation with id: " + reservation.getId().getId());
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, RESERVATION_CREATED_ROUTING_KEY, new ReservationCreated(bookId.getId(), Instant.now()));

        return reservation;
    }

    public boolean removeReservation(ReservationId reservationId, UserId userId){
        Optional<Reservation> reservationOpt = repository.getReservationById(reservationId);

        if(reservationOpt.isPresent()){
            Reservation reservation = reservationOpt.get();

            if(reservation.getUserId().getId().equals(userId.getId())){
                repository.deleteReservation(reservationId);
                logger.info("Deleted reservation with id: " + reservationId.getId());
                rabbitTemplate.convertAndSend(EXCHANGE_NAME, LOAN_RETURNED_ROUTING_KEY, new LoanReturned(reservation.getBookId().getId(), Instant.now()));
            }else{
                logger.error("User with id: " + userId.getId() + " can't remove reservation of user with id: " + reservation.getUserId().getId());
                throw new BookNotFoundException("User can't remove reservation of another user");
            }
        }
        return true;
    }

    public List<Reservation> getAllReservationsByUserId(UserId userId){
        return repository.findReservationsByUserId(userId);
    }

    public Reservation saveReservation(Reservation reservation){
        return repository.saveReservation(reservation);
    }
}

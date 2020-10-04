package com.mvucevski.lendingmanagement.service;

import com.mvucevski.lendingmanagement.domain.*;
import com.mvucevski.lendingmanagement.exceptions.BookAvailableCopiesException;
import com.mvucevski.lendingmanagement.exceptions.BookNotFoundException;
import com.mvucevski.lendingmanagement.exceptions.ReservationsLoansLimitException;
import com.mvucevski.lendingmanagement.exceptions.UserMembershipException;
import com.mvucevski.lendingmanagement.port.client.BookCatalogClient;
import com.mvucevski.lendingmanagement.repository.loans.LoansRepository;
import com.mvucevski.lendingmanagement.repository.reservations.ReservationsRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mvucevski.lendingmanagement.Constants.MAX_RESERVATIONS_PER_USER;

@Service
public class ReservationsService {

    private final ReservationsRepository repository;
    private BookCatalogClient bookCatalogClient;
    private LoansRepository loansRepository;

    public ReservationsService(@Qualifier("dbReservationsRepository") ReservationsRepository repository,
                               BookCatalogClient bookCatalogClient,
                               LoansRepository loansRepository) {
        this.repository = repository;
        this.bookCatalogClient = bookCatalogClient;
        this.loansRepository = loansRepository;
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

        return repository.saveReservation(new Reservation(bookId, user.getUserId()));
    }

    public Reservation saveReservation(Reservation reservation){
        return repository.saveReservation(reservation);
    }
}

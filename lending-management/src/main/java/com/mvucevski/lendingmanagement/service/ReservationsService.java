package com.mvucevski.lendingmanagement.service;

import com.mvucevski.lendingmanagement.domain.*;
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

    public ReservationsService(@Qualifier("dbReservationsRepository") ReservationsRepository repository) {
        this.repository = repository;
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
        //TODO Check book copies



        if(user.isMembershipExpired()){
            //TODO Exception
            System.out.println("Please start or renew your membership before making reservation!");
            throw new RuntimeException("\"Please start or renew your membership before making reservation!");
        }

        List<Reservation> reservations = repository.findReservationsByUserId(user.getUserId());

        if(reservations.size() >= MAX_RESERVATIONS_PER_USER){
            System.out.println("You have reached the limit of active reservations!");
            throw new RuntimeException("\"You have reached the limit of active reservations!");
        }

        if(reservations.stream().anyMatch(e->e.getBookId().getId().equals(bookId.getId()))){
            System.out.println("You already have active reservation for this book");
            throw new RuntimeException("\"You already have active reservation for this book\"");
        }


        return repository.saveReservation(new Reservation(bookId, user.getUserId()));
    }

    public Reservation saveReservation(Reservation reservation){
        return repository.saveReservation(reservation);
    }
}

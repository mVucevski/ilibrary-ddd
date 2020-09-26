package com.mvucevski.lendingmanagement.service;

import com.mvucevski.lendingmanagement.domain.*;
import com.mvucevski.lendingmanagement.repository.loans.LoansRepository;
import com.mvucevski.lendingmanagement.repository.reservations.ReservationsRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Reservation createReservation(BookId bookId, UserId userId){
        return repository.saveReservation(new Reservation(bookId, userId));
    }

    public Reservation saveReservation(Reservation reservation){
        return repository.saveReservation(reservation);
    }
}

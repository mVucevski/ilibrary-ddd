package com.mvucevski.lendingmanagement.service;

import com.mvucevski.lendingmanagement.domain.Loan;
import com.mvucevski.lendingmanagement.domain.LoanId;
import com.mvucevski.lendingmanagement.domain.Reservation;
import com.mvucevski.lendingmanagement.domain.ReservationId;
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

    public Reservation saveReservation(Reservation id){
        return repository.saveReservation(id);
    }
}

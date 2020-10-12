package com.mvucevski.lendingmanagement;

import com.mvucevski.lendingmanagement.domain.BookId;
import com.mvucevski.lendingmanagement.domain.Loan;
import com.mvucevski.lendingmanagement.domain.Reservation;
import com.mvucevski.lendingmanagement.domain.UserId;
import com.mvucevski.lendingmanagement.repository.loans.LoansRepository;
import com.mvucevski.lendingmanagement.repository.reservations.ReservationsRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class DataGenerator {

    private final LoansRepository loansRepository;
    private final ReservationsRepository reservationsRepository;

    public DataGenerator(@Qualifier("dbLoansRepository") LoansRepository loansRepository, @Qualifier("dbReservationsRepository") ReservationsRepository reservationsRepository) {
        this.loansRepository = loansRepository;
        this.reservationsRepository = reservationsRepository;
    }

    @PostConstruct
    @Transactional
    public void generateData(){

//       if(loansRepository.getAllLoans().isEmpty()){
//           loansRepository.saveLoan(
//                   new Loan(new BookId("7c997e8b-ea90-4be9-8487-ca35251af2ec"),  new UserId("0")));
//       }
//
//       if(reservationsRepository.getAllReservations().isEmpty()){
//          reservationsRepository.saveReservation( new Reservation(new BookId("7c997e8b-ea90-4be9-8487-ca35251af2ec"),  new UserId("1")));
//       }
    }
}

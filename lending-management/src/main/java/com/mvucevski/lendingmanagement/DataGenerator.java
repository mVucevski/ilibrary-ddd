package com.mvucevski.lendingmanagement;

import com.mvucevski.lendingmanagement.domain.*;
import com.mvucevski.lendingmanagement.port.client.UserManagementClient;
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
    private UserManagementClient userManagementClient;

    public DataGenerator(@Qualifier("dbLoansRepository") LoansRepository loansRepository, UserManagementClient userManagementClient, @Qualifier("dbReservationsRepository") ReservationsRepository reservationsRepository) {
        this.loansRepository = loansRepository;
        this.reservationsRepository = reservationsRepository;
        this.userManagementClient = userManagementClient;
    }

    @PostConstruct
    @Transactional
    public void generateData(){

    }
}

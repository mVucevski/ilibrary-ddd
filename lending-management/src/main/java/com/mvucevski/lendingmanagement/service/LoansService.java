package com.mvucevski.lendingmanagement.service;

import com.mvucevski.lendingmanagement.domain.*;
import com.mvucevski.lendingmanagement.exceptions.*;
import com.mvucevski.lendingmanagement.port.client.BookCatalogClient;
import com.mvucevski.lendingmanagement.port.client.UserManagementClient;
import com.mvucevski.lendingmanagement.repository.loans.LoansRepository;
import com.mvucevski.lendingmanagement.repository.reservations.ReservationsRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mvucevski.lendingmanagement.Constants.MAX_LOANS_PER_USER;

@Service
public class LoansService {

    private LoansRepository loansRepository;
    private ReservationsRepository reservationsRepository;
    private UserManagementClient userManagementClient;
    private BookCatalogClient bookCatalogClient;

    public LoansService(@Qualifier("dbLoansRepository") LoansRepository repository, ReservationsRepository reservationsRepository, BookCatalogClient bookCatalogClient, UserManagementClient userManagementClient) {
        this.loansRepository = repository;
        this.userManagementClient = userManagementClient;
        this.bookCatalogClient = bookCatalogClient;
        this.reservationsRepository = reservationsRepository;
    }

    public List<Loan> getAllLoans(){
        return loansRepository.getAllLoans();
    }

    public Optional<Loan> getLoanById(LoanId loanId){
        return loansRepository.getLoanById(loanId);
    }

    public Loan createLoan(BookId bookId, UserId userId){


        if(loansRepository.findLoanByUserIdAndBookId(userId, bookId).isPresent()){
            throw new LoanNotReturnedException("The user already has active loan for this book!");
        }

        if(userManagementClient.findById(userId).isMembershipExpired()){
            throw new UserMembershipException("Please start or renew your membership before making reservation!");
        }


        if(loansRepository.countActiveLoansByUserId(userId) >= MAX_LOANS_PER_USER){
            throw new ReservationsLoansLimitException("\"You have reached the limit of active loans!");
        }

       reservationsRepository.getReservationByUserIdAndBookId(userId, bookId)
               .ifPresent(e->{
                   reservationsRepository.deleteReservation(e.id());
                });

        int bookCopiesLeft = bookCatalogClient.getAvailableCopiesByBookId(bookId);
        if(bookCopiesLeft==0){
            throw new BookAvailableCopiesException("The Book with ID: '" + bookId.getId() + "' doesn't have available copies at the moment!");
        }else if(bookCopiesLeft<0){
            throw new BookNotFoundException("Book with id: " + bookId.getId() + " doesn't exist!");
        }


        return loansRepository.saveLoan(new Loan(bookId, userId));
    }

    public Loan saveLoan(Loan loan){
        return loansRepository.saveLoan(loan);
    }

    public List<Loan> getAllLoansByBookId(BookId bookId){
        return loansRepository.getAllLoansByBookId(bookId);
    }

}

package com.mvucevski.lendingmanagement.application;

import com.mvucevski.lendingmanagement.domain.event.LoanCreated;
import com.mvucevski.lendingmanagement.domain.event.LoanReturned;
import com.mvucevski.lendingmanagement.domain.model.*;
import com.mvucevski.lendingmanagement.exceptions.*;
import com.mvucevski.lendingmanagement.port.client.BookCatalogClient;
import com.mvucevski.lendingmanagement.port.client.UserManagementClient;
import com.mvucevski.lendingmanagement.domain.repository.loans.LoansRepository;
import com.mvucevski.lendingmanagement.domain.repository.reservations.ReservationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mvucevski.lendingmanagement.Constants.*;

@Service
public class LoansService {

    private LoansRepository loansRepository;
    private ReservationsRepository reservationsRepository;
    private UserManagementClient userManagementClient;
    private BookCatalogClient bookCatalogClient;
    private RabbitTemplate rabbitTemplate;
    private Logger logger;

    public LoansService(@Qualifier("dbLoansRepository") LoansRepository repository, ReservationsRepository reservationsRepository,
                        BookCatalogClient bookCatalogClient,
                        UserManagementClient userManagementClient,
                        RabbitTemplate rabbitTemplate) {
        this.loansRepository = repository;
        this.userManagementClient = userManagementClient;
        this.bookCatalogClient = bookCatalogClient;
        this.reservationsRepository = reservationsRepository;
        this.rabbitTemplate = rabbitTemplate;
        logger = LoggerFactory.getLogger(LoansService.class);
    }

    public List<Loan> getAllLoans(){
        return loansRepository.getAllLoans();
    }

    public Optional<Loan> getLoanById(LoanId loanId){
        return loansRepository.getLoanById(loanId);
    }

    public Loan createLoan(BookId bookId, String userName){

        User user = userManagementClient.findByUsername(userName);

        if(loansRepository.findActiveLoanByBookIdAndUserId(user.getId(), bookId).isPresent()){
            throw new LoanNotReturnedException("The user already has active loan for this book!");
        }

        if(user.getIsMembershipExpired()){
            throw new UserMembershipException("Please start or renew your membership before making reservation!");
        }


        if(loansRepository.countActiveLoansByUserId(user.getId()) >= MAX_LOANS_PER_USER){
            throw new ReservationsLoansLimitException("\"You have reached the limit of active loans!");
        }

       reservationsRepository.getReservationByUserIdAndBookId(user.getId(), bookId)
               .ifPresent(e->{
                   reservationsRepository.deleteReservation(e.id());
                });

        int bookCopiesLeft = bookCatalogClient.getAvailableCopiesByBookId(bookId);
        if(bookCopiesLeft==0){
            throw new BookAvailableCopiesException("The Book with ID: '" + bookId.getId() + "' doesn't have available copies at the moment!");
        }else if(bookCopiesLeft<0){
            throw new BookNotFoundException("Book with id: " + bookId.getId() + " doesn't exist!");
        }


        Loan loan = loansRepository.saveLoan(new Loan(bookId, user.getId()));
        logger.info("Created loan with id: " + loan.getId().getId());
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, LOAN_CREATED_ROUTING_KEY, new LoanCreated(bookId.getId(), Instant.now()));


        return loan;
    }

    public Loan endLoan(BookId bookId, String userName){

        User user = userManagementClient.findByUsername(userName);

        Optional<Loan> loanOpt = loansRepository.findActiveLoanByBookIdAndUserId(user.getId(), bookId);

        if(loanOpt.isPresent()){
            Loan loan = loanOpt.get();

            loan.setReturnedAt(LocalDateTime.now());

            loan = saveLoan(loan);

            logger.info("Returned loan with id: " + loan.getId().getId());
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, LOAN_RETURNED_ROUTING_KEY, new LoanReturned(loan.getBookId().getId(), Instant.now()));

            return loan;
        }else{
            logger.error("Active Loan for book with id: " + bookId.getId() + " and User: " + userName + " doesn't exist");

            throw new BookNotFoundException("Active Loan for book with id: " + bookId.getId() + " and User: " + userName + " doesn't exist");
        }

    }

    public Loan saveLoan(Loan loan){
        return loansRepository.saveLoan(loan);
    }

    public List<Loan> getAllLoansByBookId(BookId bookId){
        return loansRepository.getAllLoansByBookId(bookId);
    }

    public List<Loan> getAllLoansByUserId(UserId userId){
        return loansRepository.findLoansByUserId(userId);
    }

}

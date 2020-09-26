package com.mvucevski.lendingmanagement.service;

import com.mvucevski.lendingmanagement.domain.Loan;
import com.mvucevski.lendingmanagement.domain.LoanId;
import com.mvucevski.lendingmanagement.repository.loans.LoansRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoansService {

    private LoansRepository repository;

    public LoansService(@Qualifier("dbLoansRepository") LoansRepository repository) {
        this.repository = repository;
    }

    public List<Loan> getAllLoans(){
        return repository.getAllLoans();
    }

    public Optional<Loan> getLoanById(LoanId loanId){
        return repository.getLoanById(loanId);
    }

    public Loan saveLoan(Loan loan){
        return repository.saveLoan(loan);
    }
}

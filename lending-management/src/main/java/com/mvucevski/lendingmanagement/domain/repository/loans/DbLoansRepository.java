package com.mvucevski.lendingmanagement.domain.repository.loans;

import com.mvucevski.lendingmanagement.domain.model.BookId;
import com.mvucevski.lendingmanagement.domain.model.Loan;
import com.mvucevski.lendingmanagement.domain.model.LoanId;
import com.mvucevski.lendingmanagement.domain.model.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DbLoansRepository  implements LoansRepository{

    private final JpaLoansRepository repository;

    public DbLoansRepository(JpaLoansRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Loan> getAllLoans() {
        return repository.findAll();
    }

    @Override
    public Optional<Loan> getLoanById(LoanId loanId) {
        return repository.findById(loanId);
    }

    @Override
    public Loan saveLoan(Loan loan) {
        return repository.save(loan);
    }

    @Override
    public Boolean deleteLoan(LoanId loanId) {
        repository.deleteById(loanId);
        return true;
    }

    @Override
    public List<Loan> getAllLoansByBookId(BookId bookId) {
        return repository.findLoansByBookId(bookId);
    }

    @Override
    public List<Loan> findLoansByUserId(UserId userId) {
        return repository.findLoansByUserId(userId);
    }

    @Override
    public long countActiveLoansByUserId(UserId userId) {
        return repository.countLoansByUserIdAndReturned_AtIsNull(userId);
    }

    @Override
    public Optional<Loan> findLoanByUserIdAndBookId(UserId userId, BookId bookId) {
        return repository.findLoanByUserIdAndBookId(userId, bookId);
    }

    @Override
    public Optional<Loan> findActiveLoanByBookIdAndUserId(UserId userId, BookId bookId) {
        return repository.findActiveLoanByBookIdAndUserId(userId, bookId);
    }

    @Override
    public List<Loan> findAllByReturnedAtIsNull() {
        return repository.findAllByReturnedAtIsNull();
    }
}

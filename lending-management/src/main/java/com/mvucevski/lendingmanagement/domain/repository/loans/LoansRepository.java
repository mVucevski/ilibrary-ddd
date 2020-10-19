package com.mvucevski.lendingmanagement.domain.repository.loans;

import com.mvucevski.lendingmanagement.domain.model.BookId;
import com.mvucevski.lendingmanagement.domain.model.Loan;
import com.mvucevski.lendingmanagement.domain.model.LoanId;
import com.mvucevski.lendingmanagement.domain.model.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoansRepository {

    List<Loan> getAllLoans();

    Optional<Loan> getLoanById(LoanId loanId);

    Loan saveLoan(Loan loan);

    Boolean deleteLoan(LoanId loanId);

    List<Loan> getAllLoansByBookId(BookId bookId);

    List<Loan> findLoansByUserId(UserId userId);

    long countActiveLoansByUserId(UserId userId);

    Optional<Loan> findLoanByUserIdAndBookId(UserId userId, BookId bookId);

    Optional<Loan> findActiveLoanByBookIdAndUserId(UserId userId, BookId bookId);

    List<Loan> findAllByReturnedAtIsNull();
}

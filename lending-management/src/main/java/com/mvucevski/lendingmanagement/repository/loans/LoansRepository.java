package com.mvucevski.lendingmanagement.repository.loans;

import com.mvucevski.lendingmanagement.domain.BookId;
import com.mvucevski.lendingmanagement.domain.Loan;
import com.mvucevski.lendingmanagement.domain.LoanId;
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

}

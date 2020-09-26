package com.mvucevski.lendingmanagement.repository.loans;

import com.mvucevski.lendingmanagement.domain.BookId;
import com.mvucevski.lendingmanagement.domain.Loan;
import com.mvucevski.lendingmanagement.domain.LoanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLoansRepository extends JpaRepository<Loan, LoanId> {

    List<Loan> findLoansByBookId(BookId bookId);
}

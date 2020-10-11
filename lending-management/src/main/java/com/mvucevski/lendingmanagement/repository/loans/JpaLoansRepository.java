package com.mvucevski.lendingmanagement.repository.loans;

import com.mvucevski.lendingmanagement.domain.BookId;
import com.mvucevski.lendingmanagement.domain.Loan;
import com.mvucevski.lendingmanagement.domain.LoanId;
import com.mvucevski.lendingmanagement.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaLoansRepository extends JpaRepository<Loan, LoanId> {

    List<Loan> findLoansByBookId(BookId bookId);

    List<Loan> findLoansByUserId(UserId userId);

    @Query("select count(l) from Loan l where l.userId = :userId and l.getReturnedAt is null")
    int countLoansByUserIdAndReturned_AtIsNull(UserId userId);

    Optional<Loan> findLoanByUserIdAndBookId(UserId userId, BookId bookId);
}

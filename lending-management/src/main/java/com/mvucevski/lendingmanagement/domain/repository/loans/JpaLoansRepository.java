package com.mvucevski.lendingmanagement.domain.repository.loans;

import com.mvucevski.lendingmanagement.domain.model.BookId;
import com.mvucevski.lendingmanagement.domain.model.Loan;
import com.mvucevski.lendingmanagement.domain.model.LoanId;
import com.mvucevski.lendingmanagement.domain.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaLoansRepository extends JpaRepository<Loan, LoanId> {

    List<Loan> findLoansByBookId(BookId bookId);

    List<Loan> findLoansByUserId(UserId userId);

    @Query("select count(l) from Loan l where l.userId = :userId and l.returnedAt is null")
    long countLoansByUserIdAndReturned_AtIsNull(UserId userId);

    Optional<Loan> findLoanByUserIdAndBookId(UserId userId, BookId bookId);

    @Query("select l from Loan l where l.userId = :userId and l.bookId = :bookId and l.returnedAt is null")
    Optional<Loan> findActiveLoanByBookIdAndUserId(UserId userId, BookId bookId);

    //@Query("select l from Loan l where l.returnedAt is null")
    List<Loan> findAllByReturnedAtIsNull();
}

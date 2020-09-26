package com.mvucevski.lendingmanagement.repository.reservations;

import com.mvucevski.lendingmanagement.domain.BookId;
import com.mvucevski.lendingmanagement.domain.Loan;
import com.mvucevski.lendingmanagement.domain.Reservation;
import com.mvucevski.lendingmanagement.domain.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaReservationsRepository extends JpaRepository<Reservation, ReservationId> {

    List<Reservation> findReservationsByBookId(BookId bookId);
}

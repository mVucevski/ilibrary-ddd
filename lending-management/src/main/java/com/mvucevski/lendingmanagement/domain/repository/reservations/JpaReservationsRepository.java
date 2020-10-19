package com.mvucevski.lendingmanagement.domain.repository.reservations;

import com.mvucevski.lendingmanagement.domain.model.BookId;
import com.mvucevski.lendingmanagement.domain.model.Reservation;
import com.mvucevski.lendingmanagement.domain.model.ReservationId;
import com.mvucevski.lendingmanagement.domain.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaReservationsRepository extends JpaRepository<Reservation, ReservationId> {

    List<Reservation> findReservationsByBookId(BookId bookId);

    List<Reservation> findReservationsByUserId(UserId userId);

    Optional<Reservation> findReservationByUserIdAndBookId(UserId userId, BookId bookId);

    List<Reservation> findReservationsByEndsAtBefore(LocalDateTime date);
}

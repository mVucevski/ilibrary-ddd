package com.mvucevski.lendingmanagement.repository.reservations;

import com.mvucevski.lendingmanagement.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaReservationsRepository extends JpaRepository<Reservation, ReservationId> {

    List<Reservation> findReservationsByBookId(BookId bookId);

    List<Reservation> findReservationsByUserId(UserId userId);

    Optional<Reservation> findReservationByUserIdAndBookId(UserId userId, BookId bookId);
}

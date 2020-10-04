package com.mvucevski.lendingmanagement.repository.reservations;

import com.mvucevski.lendingmanagement.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationsRepository {

    List<Reservation> getAllReservations();

    Optional<Reservation> getReservationById(ReservationId ReservationId);

    Reservation saveReservation(Reservation Reservation);

    Boolean deleteReservation(ReservationId ReservationId);

    List<Reservation> getAllReservationsByBookId(BookId bookId);

    List<Reservation> findReservationsByUserId(UserId userId);

}

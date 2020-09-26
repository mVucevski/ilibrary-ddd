package com.mvucevski.lendingmanagement.repository.reservations;

import com.mvucevski.lendingmanagement.domain.Reservation;
import com.mvucevski.lendingmanagement.domain.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReservationsRepository extends JpaRepository<Reservation, ReservationId> {
}

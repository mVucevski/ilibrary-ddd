package com.mvucevski.lendingmanagement.repository.reservations;

import com.mvucevski.lendingmanagement.domain.BookId;
import com.mvucevski.lendingmanagement.domain.Reservation;
import com.mvucevski.lendingmanagement.domain.ReservationId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DbReservationsRepository implements ReservationsRepository {

    private final JpaReservationsRepository repository;

    public DbReservationsRepository(JpaReservationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    @Override
    public Optional<Reservation> getReservationById(ReservationId reservationId) {
        return repository.findById(reservationId);
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    @Override
    public Boolean deleteReservation(ReservationId reservationId) {
        repository.deleteById(reservationId);
        return true;
    }

    @Override
    public List<Reservation> getAllReservationsByBookId(BookId bookId) {
        return repository.findReservationsByBookId(bookId);
    }
}

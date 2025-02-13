package com.mvucevski.lendingmanagement.domain.repository.reservations;

import com.mvucevski.lendingmanagement.domain.model.BookId;
import com.mvucevski.lendingmanagement.domain.model.Reservation;
import com.mvucevski.lendingmanagement.domain.model.ReservationId;
import com.mvucevski.lendingmanagement.domain.model.UserId;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Override
    public List<Reservation> findReservationsByUserId(UserId userId) {
        return repository.findReservationsByUserId(userId);
    }

    @Override
    public Optional<Reservation> getReservationByUserIdAndBookId(UserId userId, BookId bookId) {
        return repository.findReservationByUserIdAndBookId(userId, bookId);
    }

    @Override
    public List<Reservation> getReservationsByEndsAtBefore(LocalDateTime date) {
        return repository.findReservationsByEndsAtBefore(date);
    }
}

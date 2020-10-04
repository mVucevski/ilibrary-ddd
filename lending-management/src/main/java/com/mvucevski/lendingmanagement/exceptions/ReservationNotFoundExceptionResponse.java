package com.mvucevski.lendingmanagement.exceptions;

public class ReservationNotFoundExceptionResponse {
    String reservationNotFound;

    public ReservationNotFoundExceptionResponse(String reservationNotFound) {
        this.reservationNotFound = reservationNotFound;
    }

    public String getReservationNotFound() {
        return reservationNotFound;
    }

    public void setReservationNotFound(String reservationNotFound) {
        this.reservationNotFound = reservationNotFound;
    }
}

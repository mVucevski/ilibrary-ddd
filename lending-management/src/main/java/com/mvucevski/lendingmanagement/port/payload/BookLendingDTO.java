package com.mvucevski.lendingmanagement.port.payload;

import lombok.Getter;

import java.util.List;

@Getter
public class BookLendingDTO {
    private final List<LoanDTO> loans;
    private final List<ReservationDTO> reservations;

    public BookLendingDTO(List<LoanDTO> loans, List<ReservationDTO> reservations) {
        this.loans = loans;
        this.reservations = reservations;
    }
}

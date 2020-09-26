package com.mvucevski.lendingmanagement.api;

import com.mvucevski.lendingmanagement.api.payload.AddLoanRequest;
import com.mvucevski.lendingmanagement.api.payload.AddLoanResponse;
import com.mvucevski.lendingmanagement.api.payload.AddReservationRequest;
import com.mvucevski.lendingmanagement.api.payload.AddReservationResponse;
import com.mvucevski.lendingmanagement.domain.BookId;
import com.mvucevski.lendingmanagement.domain.Loan;
import com.mvucevski.lendingmanagement.domain.Reservation;
import com.mvucevski.lendingmanagement.domain.UserId;
import com.mvucevski.lendingmanagement.service.LoansService;
import com.mvucevski.lendingmanagement.service.ReservationsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lending")
public class LendingController {

    private LoansService loansService;
    private ReservationsService reservationsService;

    public LendingController(LoansService loansService, ReservationsService reservationsService) {
        this.loansService = loansService;
        this.reservationsService = reservationsService;
    }

    @GetMapping("/loans/{bookId}")
    public List<Loan> getAllLoansByBookId(@PathVariable String bookId){
        return loansService.getAllLoansByBookId(new BookId(bookId));
    }

    @GetMapping("/reservations/{bookId}")
    public List<Reservation> getAllReservationsByBookId(@PathVariable String bookId){
        return reservationsService.getAllReservationsByBookId(new BookId(bookId));
    }

    @PostMapping("/loans/{bookId}")
    public AddLoanResponse addBookLoan(@PathVariable String bookId, @RequestBody AddLoanRequest request){
        Loan loan = loansService.createLoan(new BookId(request.getBookId()), new UserId(request.getUserId()));

        return new AddLoanResponse(
                loan.getBookId().getId(),
                loan.getUserId().getId(),
                loan.getCreatedAt(),
                loan.getDueDate());
    }

    @PostMapping("/reservations/{bookId}")
    public AddReservationResponse addBookReservation(@PathVariable String bookId, @RequestBody AddReservationRequest request){
        Reservation reservation = reservationsService.createReservation(new BookId(request.getBookId()), new UserId(request.getUserId()));

        return new AddReservationResponse(
                reservation.getBookId().getId(),
                reservation.getEndsAt());
    }


}

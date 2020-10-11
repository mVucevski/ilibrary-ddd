package com.mvucevski.lendingmanagement.api;

import com.mvucevski.lendingmanagement.api.payload.*;
import com.mvucevski.lendingmanagement.domain.*;
import com.mvucevski.lendingmanagement.service.LoansService;
import com.mvucevski.lendingmanagement.service.ReservationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lending")
public class LendingController {

    private LoansService loansService;
    private ReservationsService reservationsService;

    public LendingController(LoansService loansService, ReservationsService reservationsService) {
        this.loansService = loansService;
        this.reservationsService = reservationsService;
    }

    @GetMapping("/{bookId}")
    public BookLendingDTO getReservationsAndLoansByBookId(@PathVariable String bookId){
        List<LoanDTO> loans = loansService.getAllLoansByBookId(new BookId(bookId)).stream()
                .map(e->new LoanDTO(e.getBookId().getId(),
                        e.getUserId().getId(), e.getDueDate(),
                        e.getReturnedAt())).collect(Collectors.toList());

        List<ReservationDTO> reservations = reservationsService.getAllReservationsByBookId(new BookId(bookId))
                .stream().map(e->new ReservationDTO(e.getBookId().getId(), e.getUserId().getId(), e.getEndsAt())).collect(Collectors.toList());

        return new BookLendingDTO(loans, reservations);
    }

    @GetMapping("/loans/{bookId}")
    public List<Loan> getAllLoansByBookId(@PathVariable String bookId){
        return loansService.getAllLoansByBookId(new BookId(bookId));
    }

    @GetMapping("/reservations/{bookId}")
    public List<Reservation> getAllReservationsByBookId(@PathVariable String bookId){
        return reservationsService.getAllReservationsByBookId(new BookId(bookId));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/loans/{bookId}")
    public AddLoanResponse addBookLoan(@PathVariable String bookId,
                                       @RequestBody AddLoanRequest request){

        Loan loan = loansService.createLoan(new BookId(request.getBookId()), new UserId(request.getUserId()));

        return new AddLoanResponse(
                loan.getBookId().getId(),
                loan.getUserId().getId(),
                loan.getCreatedAt(),
                loan.getDueDate());
    }

    @GetMapping("/reservations/{bookId}/create")
    public ResponseEntity<?> addBookReservation(@PathVariable String bookId,
                                                @AuthenticationPrincipal User user){

        if(user==null){
            return new ResponseEntity<String>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        Reservation reservation = reservationsService.createReservation(new BookId(bookId), user);

        return new ResponseEntity<>(new AddReservationResponse(
                reservation.getBookId().getId(),
                reservation.getEndsAt()), HttpStatus.OK);
    }

    @GetMapping("/reservations/{reservationId}/remove")
    public ResponseEntity<String> removeReservation(@PathVariable String reservationId,
                                                    @AuthenticationPrincipal User user){

        if(user==null){
            return new ResponseEntity<String>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        reservationsService.removeReservation(new ReservationId(reservationId), user.getUserId());

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }


}

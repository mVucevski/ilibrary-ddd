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
@CrossOrigin
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
                        e.getUserId().getId(), e.getDueDate(), e.getCreatedAt(),
                        e.getReturnedAt(), e.getFee())).collect(Collectors.toList());

        List<ReservationDTO> reservations = reservationsService.getAllReservationsByBookId(new BookId(bookId))
                .stream().map(e->new ReservationDTO(e.getId().getId(),e.getBookId().getId(), e.getUserId().getId(), e.getCreatedAt(), e.getEndsAt())).collect(Collectors.toList());

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

        Loan loan = loansService.createLoan(new BookId(request.getBookId()), request.getUsername());

        return new AddLoanResponse(
                loan.getBookId().getId(),
                request.getUsername(),
                loan.getUserId().getId(),
                loan.getCreatedAt(),
                loan.getDueDate());
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/loans/{bookId}/{username}")
    public ResponseEntity<?> removeBookLoan(@PathVariable String bookId, @PathVariable String username){

        Loan loan = loansService.endLoan(new BookId(bookId), username);

        return new ResponseEntity<>("The loan for the book with ID: '" + bookId + "' was successfully returned.", HttpStatus.OK);
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

    @DeleteMapping("/reservations/{reservationId}/remove")
    public ResponseEntity<String> removeReservation(@PathVariable String reservationId,
                                                    @AuthenticationPrincipal User user){

        if(user==null){
            return new ResponseEntity<String>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        reservationsService.removeReservation(new ReservationId(reservationId), user.getId());

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public BookLendingDTO getReservationsAndLoansByUserId(@PathVariable String userId){
        List<LoanDTO> loans = loansService.getAllLoansByUserId(new UserId(userId)).stream()
                .map(e->new LoanDTO(e.getBookId().getId(),
                        e.getUserId().getId(), e.getDueDate(), e.getCreatedAt(),
                        e.getReturnedAt(), e.getFee())).collect(Collectors.toList());

        List<ReservationDTO> reservations = reservationsService.getAllReservationsByUserId(new UserId(userId))
                .stream().map(e->new ReservationDTO(e.getId().getId(),e.getBookId().getId(), e.getUserId().getId(), e.getCreatedAt(), e.getEndsAt())).collect(Collectors.toList());

        return new BookLendingDTO(loans, reservations);
    }

}

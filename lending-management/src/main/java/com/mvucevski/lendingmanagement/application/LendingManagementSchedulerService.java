package com.mvucevski.lendingmanagement.application;

import com.mvucevski.lendingmanagement.domain.model.Loan;
import com.mvucevski.lendingmanagement.domain.model.Reservation;
import com.mvucevski.lendingmanagement.domain.model.User;
import com.mvucevski.lendingmanagement.port.client.UserManagementClient;
import com.mvucevski.lendingmanagement.domain.repository.loans.LoansRepository;
import com.mvucevski.lendingmanagement.domain.repository.reservations.ReservationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LendingManagementSchedulerService {

    private ReservationsRepository reservationsRepository;
    private LoansRepository loansRepository;
    private EmailService emailService;
    private TaskScheduler taskScheduler;
    private UserManagementClient userManagementClient;
    private Logger logger;

    public LendingManagementSchedulerService(ReservationsRepository reservationsRepository,
                                             LoansRepository loansRepository,
                                             EmailService emailService,
                                             UserManagementClient userManagementClient,
                                             TaskScheduler taskScheduler) {
        this.reservationsRepository = reservationsRepository;
        this.loansRepository = loansRepository;
        this.emailService = emailService;
        this.userManagementClient = userManagementClient;
        this.taskScheduler = taskScheduler;
        logger = LoggerFactory.getLogger(LendingManagementSchedulerService.class);

        checkReservationsToRemove();
    }

    @Scheduled(cron = "0 0 12 * * *", zone = "Europe/Skopje")
    private void checkReservationsToRemove() {
        List<Reservation> reservations = reservationsRepository
                .getReservationsByEndsAtBefore(LocalDateTime.now());

        reservations.forEach(e -> {
            reservationsRepository.deleteReservation(e.getId());
            logger.info("Removing reservation with id: " + e.getId().getId());
        });
    }


    @Scheduled(cron = "0 0 12 * * MON,WED,SAT", zone = "Europe/Skopje")
    private void checkLoansToRemind() {
        System.out.println("Reminder!");

        List<Loan> loans = loansRepository.findAllByReturnedAtIsNull();

        loans.stream().filter(e -> e.getDueDate().isBefore(LocalDateTime.now().plusDays(3)))
                .forEach(e -> {
                    String msg = "";
                    if (e.getDueDate().isAfter(LocalDateTime.now())) {
                        String date = e.getDueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy 'at' hh:mm a"));
                        msg = "The due date for your loan is on " + date;
                        msg += " . Return it before that date and pay $0 fee";
                    } else {
                        msg = "Your book loan is due, please return it as soon as you can, otherwise the fee will increas. Current Fee: $" + e.getFee();
                    }

                    try {
                        User user = userManagementClient.findById(e.getUserId());

                        emailService.sendLoanReminderEmail(user.getUsername(), user.getFullName(), msg);
                        logger.info("Sending email for loan reminder for loan with id: " + e.getId().getId());
                    } catch (MessagingException messagingException) {
                        logger.error("Error occurred while trying to send email for loan reminder. Loan id: " + e.getId().getId());
                        messagingException.printStackTrace();
                    }
                });
    }

}

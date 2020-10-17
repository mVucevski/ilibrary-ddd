package com.mvucevski.lendingmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp-relay.sendinblue.com");
        mailSender.setPort(587);
        mailSender.setUsername("contact.feelthegoals@gmail.com");
        mailSender.setPassword("jATWqCnNFK1bgSXL");
        Properties props = mailSender.getJavaMailProperties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "false");
        props.setProperty("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public SimpleMailMessage templateLoanReminderMsg() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "<body style=\"background-color: #9db2c79e; text-align:center\"><h3>Reminder! \uD83D\uDD14</h3>\n" +
                        "<h4>%s</h4>\n" +
                        "Visit <a href=\\\"http://localhost:8080\\\">iLibrary</a> to see more details</body>");
        return message;
    }
}

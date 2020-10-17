package com.mvucevski.lendingmanagement.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final SimpleMailMessage simpleMailMessage;

    public EmailService(@Qualifier("getJavaMailSender") JavaMailSender emailSender, SimpleMailMessage simpleMailMessage) {
        this.emailSender = emailSender;
        this.simpleMailMessage = simpleMailMessage;
    }

    public void sendEMail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("contact.feelthegoals@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        emailSender.send(message);
    }

    public void sendLoanReminderEmail(String userEmail, String fullName, String msg) throws MessagingException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String msgContent = String.format(simpleMailMessage.getText(), msg);

        helper.setText(msgContent, true);
        helper.setTo(userEmail);
        helper.setSubject("iLibrary - Reminder For " + fullName);
        helper.setFrom("contact.feelthegoals@gmail.com");
        emailSender.send(mimeMessage);
    }

}

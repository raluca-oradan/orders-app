package com.javaAdvanced.ordersapp.EMAIL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private JavaMailSenderImpl mailSender;

    @Autowired
    public EmailService (JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void send(String to,  String content){
        try {

        // Get the default Session object.
        Session session = Session.getInstance(this.mailSender.getJavaMailProperties(),
                                              new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("test.aplicatie.java@gmail.com","testJava1234@");
            }
                                              });

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            // Set From: header field of the header.
            helper.setFrom("test.aplicatie.java@gmail.com");

            // Set To: header field of the header.
            helper.setTo(to);

            // Set Subject: header field
            helper.setSubject("Welcome to your new account!");

            // Now set the actual message
            helper.setText(content,true);

            // Send message
            mailSender.send(message);

        } catch (MessagingException e) {
            LOGGER.error("failed to send email",e); //clientul nu primeste eroarea, doar cel care trimite(programul)
            throw  new IllegalStateException("Failed to send email");
        }
    }
}


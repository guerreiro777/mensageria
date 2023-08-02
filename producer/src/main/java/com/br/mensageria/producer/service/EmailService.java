package com.br.mensageria.producer.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Log
@Service
public class EmailService {

    @Value("${spring.mail.enabled}")
    private Boolean enabled;

    @Autowired
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailFromTemplate(String fromName,
                                      String fromAddress,
                                      String to,
                                      String subject,
                                      String htmlTemplate) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress(fromAddress, fromName));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);

        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        if (enabled) {
            log.info("Send mail to " + to);
            javaMailSender.send(message);
        } else {
            log.info("Email not enabled");
        }
    }
}

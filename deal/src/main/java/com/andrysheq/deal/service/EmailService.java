package com.andrysheq.deal.service;

import java.security.SecureRandom;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final String EMAIL_HOST = "smtp.mail.ru";
    private static final String EMAIL_USERNAME = "neo-bank-andrysheq@mail.ru";
    private static final String EMAIL_PASSWORD = "neoaexamplepswrd123";

    public void sendEmail(String to) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", EMAIL_HOST);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Подписание документов на кредит ");
        message.setText("Ваш код подтверждения " + generateRandomCode());

        Transport.send(message);
    }

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}


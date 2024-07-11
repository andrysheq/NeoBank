package com.andrysheq.deal.service;

import java.io.*;
import java.security.SecureRandom;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final String EMAIL_HOST = "smtp.mail.ru";
    private static final String EMAIL_USERNAME = "andrey.andreych@bk.ru"; //почта mail (личная, так как нельзя создать ящик без уникального номера телефона)

    public void sendEmail(String to, String code, String signLink) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", EMAIL_HOST);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        String EMAIL_PASSWORD;
        try {
            //файл на компьютере с паролем для приложений для почты
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\javaandreych.txt")));
            EMAIL_PASSWORD = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Подписание документов на кредит ");
        message.setText("Ваш код подтверждения : " + code + "\nВот ссылка для подтверждения : "+signLink);

        Transport.send(message);
    }

    public String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}


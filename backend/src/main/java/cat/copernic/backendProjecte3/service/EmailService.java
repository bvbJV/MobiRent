package cat.copernic.backendProjecte3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRecoveryEmail(String to, String temporaryPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Recovery");
        message.setText(
                "Your temporary password is: " + temporaryPassword +
                "\nPlease log in and change it as soon as possible."
        );

        mailSender.send(message);
    }
}
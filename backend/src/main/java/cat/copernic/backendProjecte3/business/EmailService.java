package cat.copernic.backendProjecte3.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordRecoveryEmail(String to, String userName, String temporaryPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("AppVehicles - Password Recovery");

        message.setText(
                "Hello " + userName + ",\n\n" +
                "Your temporary password is:\n\n" +
                temporaryPassword + "\n\n" +
                "Login and change it as soon as possible.\n\n" +
                "AppVehicles Team"
        );

        mailSender.send(message);
    }
}
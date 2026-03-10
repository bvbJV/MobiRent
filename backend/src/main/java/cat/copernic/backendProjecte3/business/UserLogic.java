package cat.copernic.backendProjecte3.business;

import cat.copernic.backendProjecte3.config.PasswordHasher;
import cat.copernic.backendProjecte3.entities.Usuari;
import cat.copernic.backendProjecte3.enums.UserRole;
import cat.copernic.backendProjecte3.exceptions.AccesDenegatException;
import cat.copernic.backendProjecte3.repository.UsuariRepository;
import static jakarta.persistence.GenerationType.UUID;
import java.util.Optional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author manel
 */
@Service
public class UserLogic {

    @Autowired
    UsuariRepository usuariRepository;

    /**
     * *
     *
     * @param email
     * @param rawPassword
     * @return
     * @throws AccesDenegatException
     */
    public Optional<UserRole> login(String email, String rawPassword) throws AccesDenegatException {

        UserRole ret = UserRole.NONE;

        Usuari user = usuariRepository.findByEmail(email).orElseThrow(() -> new AccesDenegatException("Usuari no existeix"));

        if (PasswordHasher.check(rawPassword, user.getPassword())) {
            ret = user.getRol();
        } else {
            throw new AccesDenegatException("Bad Password");
        }

        return Optional.of(ret);
    }

    public Optional<UserRole> getRole(String email) throws AccesDenegatException {

        Usuari user = usuariRepository.findByEmail(email).orElseThrow(() -> new AccesDenegatException("Usuari no existeix"));

        return Optional.of(user.getRol());
    }
    @Autowired
    EmailService emailService;

    public void recoverPassword(String email) {

        Optional<Usuari> userOptional = usuariRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return;
        }

        Usuari user = userOptional.get();

        String tempPassword = generateTemporaryPassword();

        user.setPassword(PasswordHasher.encode(tempPassword));

        usuariRepository.save(user);

        emailService.sendPasswordRecoveryEmail(
                user.getEmail(),
                user.getNomComplet(),
                tempPassword
        );
    }

    private String generateTemporaryPassword() {

        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789";
        StringBuilder pass = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * chars.length());
            pass.append(chars.charAt(index));
        }

        return pass.toString();
    }

}

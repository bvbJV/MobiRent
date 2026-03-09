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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public void recoverPassword(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return;
        }

        User user = userOptional.get();

        String temporaryPassword = UUID.randomUUID().toString().substring(0, 8);

        user.setPassword(passwordEncoder.encode(temporaryPassword));

        userRepository.save(user);

        emailService.sendRecoveryEmail(email, temporaryPassword);
    }

}

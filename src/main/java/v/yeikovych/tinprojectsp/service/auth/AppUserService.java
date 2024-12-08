package v.yeikovych.tinprojectsp.service.auth;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import v.yeikovych.tinprojectsp.dto.auth.RegisterUserDto;
import v.yeikovych.tinprojectsp.model.auth.AppUser;
import v.yeikovych.tinprojectsp.repository.AppUserRepository;

import javax.naming.AuthenticationException;

@Service
@Transactional
@Slf4j
public class AppUserService {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public AppUserService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public AppUser registerNewUserAccount(RegisterUserDto dto) throws AuthenticationException {
        if (emailExists(dto.getEmail())) {
            throw new AuthenticationException("User with given email [" + dto.getEmail() + "] already exists!");
        }

        AppUser user = AppUser.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build();

        repository.save(user);

        log.info("Got from Registration: " + user);

        return user;
    }

    private boolean emailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }
}

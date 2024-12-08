package v.yeikovych.tinprojectsp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import v.yeikovych.tinprojectsp.model.auth.AppUser;
import v.yeikovych.tinprojectsp.model.auth.SecurityAppUser;
import v.yeikovych.tinprojectsp.repository.AppUserRepository;


@Service
@Slf4j
public class AppUserAuthenticationManager implements AuthenticationManager{

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserAuthenticationManager(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        AppUser userAuth = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, userAuth.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        SecurityAppUser secUser = new SecurityAppUser(userAuth);

        return new UsernamePasswordAuthenticationToken(secUser, password, secUser.getAuthorities());
    }
}

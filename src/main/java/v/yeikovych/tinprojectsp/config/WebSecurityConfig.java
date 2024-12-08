package v.yeikovych.tinprojectsp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final CustomLogoutHandler logoutHandler;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, CustomLogoutHandler logoutHandler, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.logoutHandler = logoutHandler;
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/webjars/**").permitAll()
                                .requestMatchers("/", "/login**", "/error/**", "/callback/", "/register", "/itns", "/students", "/teachers", "/itns/detailed/**").permitAll()
                                .anyRequest().authenticated())
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessUrl("/login?logout")
                                .permitAll())
                .formLogin(form -> form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successForwardUrl("/")
                        .failureUrl("/login?error"));

        return http.build();
    }

}

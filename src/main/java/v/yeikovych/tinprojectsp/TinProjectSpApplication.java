package v.yeikovych.tinprojectsp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import v.yeikovych.tinprojectsp.repository.AppUserRepository;

@Slf4j
@EnableJpaRepositories(basePackageClasses = AppUserRepository.class)
@SpringBootApplication
public class TinProjectSpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinProjectSpApplication.class, args);
    }

}

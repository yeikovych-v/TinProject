package v.yeikovych.tinprojectsp.controller;

import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import v.yeikovych.tinprojectsp.service.auth.SecurityService;

@Controller
@RequestMapping("/")
@Slf4j
public class MainPage {

    private final SecurityService securityService;

    public MainPage(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public String getMainPage(Model model) {
        log.info("GET: /");
        securityService.getUserFromSecurity().ifPresent(user -> model.addAttribute("username", user.getUsername()));
        return "main";
    }

    @PostMapping
    public String postMainPage(Model model) {
        log.info("POST: /");
        securityService.getUserFromSecurity().ifPresent(user -> model.addAttribute("username", user.getUsername()));
        return "main";
    }
}

package v.yeikovych.tinprojectsp.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import v.yeikovych.tinprojectsp.service.auth.AppUserService;
import v.yeikovych.tinprojectsp.service.auth.SecurityService;


@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @GetMapping
    public String displayLoginPage() {
        log.info("GET: /login");
        return "login";
    }

}

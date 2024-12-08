package v.yeikovych.tinprojectsp.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import v.yeikovych.tinprojectsp.dto.auth.RegisterUserDto;
import v.yeikovych.tinprojectsp.model.auth.AppUser;
import v.yeikovych.tinprojectsp.service.auth.AppUserService;
import v.yeikovych.tinprojectsp.service.auth.SecurityService;

import javax.naming.AuthenticationException;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegisterController {

    private final AppUserService userService;
    private final SecurityService securityService;

    @Autowired
    public RegisterController(AppUserService appUserService, SecurityService securityService) {
        this.userService = appUserService;
        this.securityService = securityService;
    }

    @GetMapping
    public String displayRegisterPage(Model model) {
        log.info("GET: /register");
        model.addAttribute("user", new RegisterUserDto());
        return "register";
    }

    @PostMapping
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid RegisterUserDto userDto,
            Errors errors,
            HttpServletRequest request,
            ModelAndView mav
    ) {
        log.info("POST: /register");

        if (errors.hasErrors()) {
            log.info("Errors: {}", errors.getAllErrors());
            return mav;
        }

        AppUser user;
        try {
            user = userService.registerNewUserAccount(userDto);
        } catch (AuthenticationException e) {
            log.info("User already exists.");
            mav.addObject("message", "An account for that email already exists.");
            return mav;
        }

        securityService.authenticateRegisteredUser(user, request);

        return new ModelAndView("main", "username", user.getUsername());
    }
}

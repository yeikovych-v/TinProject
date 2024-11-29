package v.yeikovych.tinprojectsp.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainPage {

    @GetMapping
    public String getMainPage(Model model) {
        return "main";
    }
}

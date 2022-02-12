package ua.com.alevel.module_3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    public String homePage() {
        return "redirect:/users";
    }
}

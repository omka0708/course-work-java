package com.mirea.coursework.controllers;

import com.mirea.coursework.services.HeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private HeaderService headerService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        model.addAttribute("hiddenEl", headerService.isUser());
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "О нас");
        model.addAttribute("hiddenEl", headerService.isUser());
        return "about";
    }
}

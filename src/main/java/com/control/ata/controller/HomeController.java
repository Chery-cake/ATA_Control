package com.control.ata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String init(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showIndex(){
        return "home";
    }

}

package com.control.ata.web.controller;

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
        return "index.html";
    }

}

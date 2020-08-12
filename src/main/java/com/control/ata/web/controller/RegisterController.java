package com.control.ata.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String abrirCadastro(){
        return "register.html";
    }

    @GetMapping("/login")
    public String abrirLogin(){
        return "login.html";
    }

}

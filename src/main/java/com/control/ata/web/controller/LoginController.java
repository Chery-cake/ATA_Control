package com.control.ata.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pessoa")
public class LoginController {

    // ======================================LOGIN=============================================

    @GetMapping("/login")
    public String abrirLogin() {
        return "chaves";
    }

}

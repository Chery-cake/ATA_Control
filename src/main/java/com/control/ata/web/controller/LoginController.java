package com.control.ata.web.controller;

import com.control.ata.repository.pessoa.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private PessoaRepository pessoaRepository;


    // ======================================LOGIN=============================================

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/pessoa")
    public String pessoaDetail(Model model) {
        model.addAttribute("pessoas", pessoaRepository.findAll());
        return "pessoa";
    }
}

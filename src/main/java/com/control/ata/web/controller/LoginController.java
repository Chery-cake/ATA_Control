package com.control.ata.web.controller;

import com.control.ata.security.entity.ConfirmationToken;
import com.control.ata.security.repository.ConfirmationTokenRepository;
import com.control.ata.security.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/pessoa")
public class LoginController {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private PessoaService pessoaService;

    // ======================================LOGIN=============================================

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register/confirm")
    public String confirmMail(@RequestParam("toke") String token){
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);

        optionalConfirmationToken.ifPresent(pessoaService::confirmarPessoa);
        return "redirect:/";
    }

}

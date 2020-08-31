package com.control.ata.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    // ======================================PERFIL=============================================

    @GetMapping("/perfil")
    public String perfil(){
        return "perfil";
    }

    // ======================================AJUSTAR PERFIL=============================================

    @GetMapping("/ajustar")
    public String ajustarPerfil(){
        return "ajustar_perfil";
    }

}

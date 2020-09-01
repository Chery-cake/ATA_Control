package com.control.ata.web.controller;

import com.control.ata.security.entity.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalAttributesController {

    @ModelAttribute("usuario")
    public Usuario pessoaDetail(Authentication authentication) {
        if (authentication != null) {
            return (Usuario) authentication.getPrincipal();
        }
        return null;
    }

}

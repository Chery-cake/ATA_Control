package com.control.ata.web.controller;

import com.control.ata.security.entity.Usuario;
import com.control.ata.security.enuns.UserRole;
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

    @ModelAttribute("ROLE_ADMIN")
    public UserRole getUserRoleADM(){
        return UserRole.ROLE_ADMIN;
    }

    @ModelAttribute("ROLE_USER")
    public UserRole getUserRoleUSER(){
        return UserRole.ROLE_USER;
    }

    @ModelAttribute("ROLE_PLANILHA")
    public UserRole getUserRolePLAN(){
        return UserRole.ROLE_PLANILHA;
    }
}


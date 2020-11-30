package com.control.ata.web.controller;

import com.control.ata.security.enuns.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PerfilController {

    // ======================================PERFIL=============================================

    @GetMapping("/perfil")
    public String perfil() {
        return "perfil";
    }

    // ======================================LOGIN=============================================

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ======================================MODEL ATTRIBUTES=============================================
/*

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
*/


}

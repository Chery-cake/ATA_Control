package com.control.ata.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCrypt {

    public static String gerarBCrypt(String senha) {
        if (senha == null) {
            return null;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(senha);
    }

    public static boolean senhaValida(String senha, String senhaEncoded) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(senha, senhaEncoded);
    }
}

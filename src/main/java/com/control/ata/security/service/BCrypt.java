package com.control.ata.security.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCrypt {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String gerarBCrypt(String senha) {
        if (senha == null) {
            return null;
        }

        return bCryptPasswordEncoder.encode(senha);
    }

    public static boolean senhaValida(String senha, String senhaEncoded) {
        return bCryptPasswordEncoder.matches(senha, senhaEncoded);
    }
}

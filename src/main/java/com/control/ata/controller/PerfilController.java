package com.control.ata.controller;

import com.control.ata.security.entity.ConfirmationToken;
import com.control.ata.security.entity.Usuario;
import com.control.ata.security.repository.ConfirmationTokenRepository;
import com.control.ata.security.repository.UsuarioRepository;
import com.control.ata.security.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

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

    // ======================================CONFIRMATION TOKEN=============================================

    @GetMapping("/login/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(
                token);

        optionalConfirmationToken.ifPresent(usuarioService::confirmUser);
        return "redirect:/login";
    }

    // ======================================PASSWORD RECOVERY=============================================

    @GetMapping("/recuperar/senha")
    public String passwordRecovery() {
        return "recuperar_senha";
    }

    @GetMapping("/recuperar/email")
    public String RecoveryEmail() {
        return "/verifica_email";
    }

    @PostMapping("/recuperar/email")
    public ResponseEntity<?> RecoveryEmailVeri(@Valid @RequestBody String json, BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        String email = jacksonObjectMapper.readValue(decodedJson, String.class);

        usuarioService.recoverPassword(email);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/trocar/senha")
    public String atualizaSenha(@Valid @RequestBody String json, @RequestParam("token") String token, BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        String senha = jacksonObjectMapper.readValue(decodedJson, String.class);

        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(
                token);

        optionalConfirmationToken.ifPresent(confirmationToken -> usuarioService.changePassword(confirmationToken, senha));

        return "redirect:/login";
    }

    // ======================================NOVO ADMINISTRADOR=============================================

    @GetMapping("/novo/administrador")
    public String novoAdm() {
        return "novo_adm";
    }

    @PostMapping("/save/administrador")
    public ResponseEntity<?> saveAdm(@Valid @RequestBody String json,
                                     BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        PerfilController.Adm adm = jacksonObjectMapper.readValue(decodedJson, PerfilController.Adm.class);

        Usuario usuario = usuarioRepository.getOne(adm.id_usuario);
        usuario.setEmail(adm.username);
        usuario.setPassword(adm.password);

        usuarioService.updateUser(usuario);

        return ResponseEntity.ok().build();
    }

    // ======================================FUNCTIONS=============================================

    private ResponseEntity<?> getErrors(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errors);
        }
        return null;
    }

    // ======================================CLASSES=============================================

    private static class Adm {
        public Integer id_usuario;
        public String username;
        public String password;

        public Adm(Integer id_usuario, String username, String password) {
            this.id_usuario = id_usuario;
            this.username = username;
            this.password = password;
        }

        public Adm() {
        }
    }

}

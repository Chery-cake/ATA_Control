package com.control.ata.security.service;

import com.control.ata.security.entity.ConfirmationToken;
import com.control.ata.security.entity.Usuario;
import com.control.ata.security.repository.ConfirmationTokenRepository;
import com.control.ata.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    void sendConfirmationMail(String userMail, String token) {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Confirmação de email");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Para ativar a sua conta acesse: " + "http://localhost:8080/login/confirm?token="
                        + token);

        emailSenderService.sendEmail(mailMessage);
    }

    void sendPasswordRecoveryMail(String userMail, String token) {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Recuperação da senha");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Para recuperar a sua senha acesse: " + "http://localhost:8080/recuperar/senha?token="
                        + token);

        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Optional<Usuario> optionalUser = usuarioRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new UsernameNotFoundException(
                MessageFormat.format("User with email {0} cannot be found.", email)));

    }

    public Usuario updateUser(Usuario usuario) {

        Usuario usuarioBd = usuarioRepository.getOne(usuario.getId());

        final String encryptedPassword = BCrypt.gerarBCrypt(usuario.getPassword());
        usuario.setPassword(encryptedPassword);

        usuarioBd.setPassword(encryptedPassword);
        usuarioBd.setEmail(usuario.getEmail());
        usuarioBd.setPessoa(usuario.getPessoa());

        return usuarioRepository.save(usuarioBd);
    }

    public Usuario signUpUser(Usuario usuario) {

        final String encryptedPassword = BCrypt.gerarBCrypt(usuario.getPassword());

        usuario.setPassword(encryptedPassword);

        final Usuario createdUser = usuarioRepository.save(usuario);

        final ConfirmationToken confirmationToken = new ConfirmationToken(usuario);

        confirmationTokenRepository.save(confirmationToken);

        sendConfirmationMail(usuario.getEmail(), confirmationToken.getConfirmationToken());

        return createdUser;
    }

    public void confirmUser(ConfirmationToken confirmationToken) {

        final Usuario user = confirmationToken.getUser();

        user.setEnabled(true);

        usuarioRepository.save(user);

        confirmationTokenRepository.delete(confirmationToken);
    }

    public void recoverPassword(String email) {

        Usuario usuario = usuarioRepository.getUsuarioByEmail(email);

        final ConfirmationToken confirmationToken = new ConfirmationToken(usuario);

        confirmationTokenRepository.save(confirmationToken);

        sendPasswordRecoveryMail(usuario.getEmail(), confirmationToken.getConfirmationToken());
    }
}

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
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    void sendConfirmationMail(String userMail, String token) {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Thank you for registering. Please click on the below link to activate your account." + "http://localhost:8080/sign-up/confirm?token="
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

        usuario.setEnabled(true);//todo resolver

        final Usuario createdUser = usuarioRepository.save(usuario);

//        final ConfirmationToken confirmationToken = new ConfirmationToken(usuario);

//        confirmationTokenRepository.save(confirmationToken);

//        sendConfirmationMail(usuario.getEmail(), confirmationToken.getConfirmationToken());//todo arrumar
//        confirmUser(confirmationToken);//todo remover

        return createdUser;
    }

    void confirmUser(ConfirmationToken confirmationToken) {

        final Usuario user = confirmationToken.getUser();

        user.setEnabled(true);

        usuarioRepository.save(user);

        confirmationTokenRepository.delete(confirmationToken);
    }
}

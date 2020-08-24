package com.control.ata.security.service;

import com.control.ata.dao.PessoaDAO;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.repository.pessoa.PessoaRepository;
import com.control.ata.security.entity.ConfirmationToken;
import com.control.ata.security.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PessoaService implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private PessoaDAO pessoaDAO;

    void sendConfirmationMail(String userMail, String token) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Link de Confirmacao!");
        mailMessage.setFrom("<MAIL>");//todo colocar um email
        mailMessage.setText(
                "Obrigado por se registrar. Por favor entre no link abaixo apara ativar a sua conta." + "http://localhost:8080/sign-up/confirm?token="
                        + token);
        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<Pessoa> optionalUser = pessoaRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new UsernameNotFoundException(
                MessageFormat.format("Usuario com o email {0} nao foi encontrado.", email)));
    }

    public void singUpPessoa(Pessoa pessoa){
        ConfirmationToken confirmationToken = new ConfirmationToken(pessoa);
        confirmationTokenRepository.save(confirmationToken);
        sendConfirmationMail(pessoa.getEmail(), confirmationToken.getConfirmationToken());
    }

    public Pessoa confirmarPessoa(ConfirmationToken confirmationToken) {
        final Pessoa pessoa = confirmationToken.getPessoa();
        pessoa.setEnabled(true);
        pessoaRepository.save(pessoa);
        confirmationTokenRepository.delete(confirmationToken);
        return pessoa;
    }
}

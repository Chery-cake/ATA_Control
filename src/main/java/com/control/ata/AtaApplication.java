package com.control.ata;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.pessoa.PessoaRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
import com.control.ata.security.entity.Usuario;
import com.control.ata.security.enuns.UserRole;
import com.control.ata.security.repository.UsuarioRepository;
import com.control.ata.security.service.BCrypt;
import com.control.ata.service.PopulateBD;
import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.Date;

@ImportResource(locations = "classpath:dwr-spring.xml")
@SpringBootApplication
public class AtaApplication implements CommandLineRunner {

    @Autowired
    private PopulateBD populateBD;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaCompeticaoRepository categoriaCompeticaoRepository;

    public static void main(String[] args) {
        SpringApplication.run(AtaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        populateBD.populate();
        Singleton.getSingleton();
        System.out.println("TERMINOU");

        Pessoa pessoa = new Pessoa("ADMIN", "", false, new Date(), 0,
                                   "NumberWorld", "NumberBrasil", false,
                                   "telefone", null, null, null);

        pessoaRepository.save(pessoa);

        Usuario usuario = new Usuario(pessoa, "email", "root");
        usuario.setUserRole(UserRole.ROLE_ADMIN);
        usuario.setPassword(BCrypt.gerarBCrypt(usuario.getPassword()));
        usuario.setEnabled(true);

        usuarioRepository.save(usuario);

        //todo remover
        categoriaCompeticaoRepository.save(new CategoriaCompeticao("nome", false, false, 0,
                                                                   0, 0, 0, 0));

    }

    @Bean
    public ServletRegistrationBean<DwrSpringServlet> dwrSpringServlet() {
        DwrSpringServlet dwrServlet = new DwrSpringServlet();

        ServletRegistrationBean<DwrSpringServlet> registrationBean =
                new ServletRegistrationBean<>(dwrServlet, "/dwr/*");

        registrationBean.addInitParameter("debug", "true");
        registrationBean.addInitParameter("activeReverseAjaxEnabled", "true");

        return registrationBean;
    }

}
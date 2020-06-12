package com.control.ata;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.model.endereco.*;
import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.ArrayList;

@ImportResource(locations = "classpath:dwr-spring.xml")
@SpringBootApplication
public class AtaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AtaApplication.class, args);
    }

    @Autowired
    private EnderecoDAO enderecoDAO;

    @Override
    public void run(String... args) throws Exception {

        Endereco e = new Endereco("rua", new Bairro("bairro", new
                Cidade("cidade", new Estado("estado", new Pais("pais")))));

        ArrayList<Endereco> enderecos = new ArrayList<>();
        enderecos.add(e);
        enderecos.add(new Endereco("rua1", new Bairro("bairro1", new
                Cidade("cidade1", new Estado("estado2", new Pais("pais3"))))));
        enderecos.add(new Endereco("rua2", new Bairro("bairro1", new
                Cidade("cidade1", new Estado("estado2", new Pais("pais3"))))));
        enderecos.add(new Endereco("rua3", new Bairro("bairro1", new
                Cidade("cidade1", new Estado("estado2", new Pais("pais3"))))));

        enderecoDAO.saveAll(enderecos);

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

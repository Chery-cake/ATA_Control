package com.control.ata;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.model.endereco.*;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.pessoa.Telefone;
import com.control.ata.repository.pessoa.FaixaRepository;
import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.ArrayList;
import java.util.Date;

@ImportResource(locations = "classpath:dwr-spring.xml")
@SpringBootApplication
public class AtaApplication implements CommandLineRunner {

    @Autowired
    private EnderecoDAO enderecoDAO;
    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private FaixaRepository faixaRepository;

    public static void main(String[] args) {
        SpringApplication.run(AtaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Endereco endereco = new Endereco("rua", new Bairro("bairro", new
                Cidade("cidade", new Estado("estado", new Pais("pais")))));

        ArrayList<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);

        enderecoDAO.saveAll(enderecos);

        Faixa faixa = new Faixa("faixa");
        faixaRepository.save(faixa);

        Pessoa pessoa = new Pessoa("nome", "sobrenome", false, new Date(), "usuario",
                "senha", 1, "foto", "world", "brasil", faixa, endereco);

        Telefone telefone = new Telefone("telefone", false, pessoa);

        ArrayList<Telefone> telefones = new ArrayList<>();

        telefones.add(telefone);
        telefones.add(new Telefone("telefone1", false, pessoa));
        telefones.add(new Telefone("telefone2", false, pessoa));

        pessoa.setTelefoneCollection(telefones);

        ArrayList<Pessoa> pessoas = new ArrayList<>();

        pessoas.add(pessoa);
        Pessoa pessoa1 = new Pessoa("nome1", "sobrenome", false, new Date(), "usuario",
                "senha", 1, "foto", "world", "brasil", faixa, endereco);
        telefones = new ArrayList<>();
        telefones.add(new Telefone("telefone1", false, pessoa1));
        telefones.add(new Telefone("telefone2", false, pessoa1));
        telefones.add(new Telefone("telefone3", false, pessoa1));
        pessoa1.setTelefoneCollection(telefones);
        pessoas.add(pessoa1);
        Pessoa pessoa2 = new Pessoa("nome2", "sobrenome", false, new Date(), "usuario",
                "senha", 1, "foto", "world", "brasil", faixa, endereco);
        telefones = new ArrayList<>();
        telefones.add(new Telefone("telefone1", false, pessoa2));
        telefones.add(new Telefone("telefone2", false, pessoa2));
        telefones.add(new Telefone("telefone3", false, pessoa2));
        pessoa2.setTelefoneCollection(telefones);
        pessoas.add(pessoa2);
        Pessoa pessoa3 = new Pessoa("nome3", "sobrenome", false, new Date(), "usuario",
                "senha", 1, "foto", "world", "brasil", faixa, endereco);
        telefones = new ArrayList<>();
        telefones.add(new Telefone("telefone1", false, pessoa3));
        telefones.add(new Telefone("telefone2", false, pessoa3));
        telefones.add(new Telefone("telefone3", false, pessoa3));
        pessoa3.setTelefoneCollection(telefones);
        pessoas.add(pessoa3);

        try {
            pessoaDAO.saveAll(pessoas);
        }catch (Exception e){
            System.out.println(e);
        }


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

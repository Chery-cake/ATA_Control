package com.control.ata;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.torneio.RodadaJuizRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import com.control.ata.service.PopulateBD;
import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = "classpath:dwr-spring.xml")
@SpringBootApplication
public class AtaApplication implements CommandLineRunner {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private TorneioRepository torneioRepository;
    @Autowired
    private RodadaJuizRepository rodadaJuizRepository;
    @Autowired
    private FaixaRepository faixaRepository;

    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;
    @Autowired
    private EnderecoDAO enderecoDAO;
    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private PopulateBD populateBD;

    public static void main(String[] args) {
        SpringApplication.run(AtaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        populateBD.populate();

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

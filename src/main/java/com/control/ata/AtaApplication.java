package com.control.ata;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.model.endereco.Bairro;
import com.control.ata.model.endereco.Cidade;
import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.pessoa.Telefone;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.model.torneio.Torneio;
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

import java.util.ArrayList;
import java.util.Date;

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

        Cidade cidade = cidadeRepository.getOne(1);

        Endereco endereco = new Endereco("rua", new Bairro("bairro", cidade));

        ArrayList<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);

        enderecoDAO.saveAll(enderecos);

        Torneio torneio = new Torneio(new Date(), new Date(), endereco);

        torneioRepository.save(torneio);

        Faixa faixa = faixaRepository.getOne(1);

        Pessoa pessoa = new Pessoa("nome", "sobrenome", false, new Date(), "usuario",
                                   "senha", 1, "foto", "world", "brasil", true, faixa, endereco);

        Telefone telefone = new Telefone("telefone", false, pessoa);

        ArrayList<Telefone> telefones = new ArrayList<>();

        telefones.add(telefone);

        pessoa.setTelefoneCollection(telefones);

        ArrayList<Pessoa> pessoas = new ArrayList<>();

        pessoas.add(pessoa);
        pessoas.add(new Pessoa("nome", "sobrenome", false, new Date(), "usuario",
                               "senha", 1, "foto", "world", "brasil", true, faixa, endereco));
        pessoas.add(new Pessoa("nome", "sobrenome", false, new Date(), "usuario",
                               "senha", 1, "foto", "world", "brasil", true, faixa, endereco));
        pessoas.add(new Pessoa("nome", "sobrenome", false, new Date(), "usuario",
                               "senha", 1, "foto", "world", "brasil", true, faixa, endereco));

        try {
            pessoaDAO.saveAll(pessoas);
        } catch (Exception e) {
            System.out.println(e);
        }

        Juiz juiz = new Juiz(pessoa);

        try {
            tipoPessoaDAO.save(juiz);
        } catch (Exception e) {
            System.out.println(e);
        }


        RodadaJuiz rodadaJuiz = new RodadaJuiz(torneio, juiz);

        rodadaJuizRepository.save(rodadaJuiz);

        try {
//            pessoaDAO.deleteAll(pessoas);
//            tipoPessoaDAO.delete(juiz);
        } catch (Exception e) {
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

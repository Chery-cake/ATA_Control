package com.control.ata;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.dao.TimeDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.model.endereco.Academia;
import com.control.ata.model.endereco.Bairro;
import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.Time;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Torneio;
import com.control.ata.repository.endereco.AcademiaRepository;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.time.RingueTimeRepository;
import com.control.ata.repository.tipo_pessoa.CompetidorRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
import com.control.ata.repository.torneio.CategoriaTorneioRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import com.control.ata.service.PopulateBD;
import com.control.ata.service.RingueService;
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
    private PopulateBD populateBD;


    @Autowired
    private TorneioRepository torneioRepository;
    @Autowired
    private FaixaRepository faixaRepository;
    @Autowired
    private CategoriaCompeticaoRepository categoriaCompeticaoRepository;
    @Autowired
    private CategoriaTorneioRepository categoriaTorneioRepository;
    @Autowired
    private AcademiaRepository academiaRepository;
    @Autowired
    private TimeDAO timeDAO;
    @Autowired
    private RingueService ringueService;
    @Autowired
    private RingueTimeRepository ringueTimeRepository;
    @Autowired
    private CompetidorRepository competidorRepository;

    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;
    @Autowired
    private EnderecoDAO enderecoDAO;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private PessoaDAO pessoaDAO;

    public static void main(String[] args) {
        SpringApplication.run(AtaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        populateBD.populate();
        Singleton.getSingleton();

        try {

            Endereco endereco = new Endereco("rua", new Bairro("bairro", cidadeRepository.getOne(1)));
            endereco = enderecoDAO.save(endereco);

            Torneio torneio = new Torneio(new Date(), new Date(), 5, endereco, categoriaTorneioRepository.getOne(5));
            torneioRepository.save(torneio);

            ArrayList<CategoriaCompeticao> categoriaCompeticaoArrayList = new ArrayList<>();
            categoriaCompeticaoArrayList.add(new CategoriaCompeticao("nome", false, false, 1, 1, 1, 1, 1));
            categoriaCompeticaoArrayList = new ArrayList<>(
                    categoriaCompeticaoRepository.saveAll(categoriaCompeticaoArrayList));

            Pessoa pessoaIns = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                          "ataWorld", "ataBrasil", true, faixaRepository.getOne(1), endereco);

            pessoaIns = pessoaDAO.save(pessoaIns);

            Academia academia = new Academia("academia", endereco);

            academia = academiaRepository.save(academia);

            Instrutor instrutor = new Instrutor(academia, pessoaIns);

            ArrayList<Instrutor> instrutorArrayList = new ArrayList<>();
            instrutorArrayList.add(instrutor);

            instrutorArrayList = (ArrayList<Instrutor>) tipoPessoaDAO.saveAll(instrutorArrayList);

            instrutor = instrutorArrayList.get(0);

            for (int l = 0; l < 100; l++) {

                System.out.println("Loop:" + l);

                ArrayList<Time> timeArrayList = new ArrayList<>();

                for (int t = 0; t <= l; t++) {

                    ArrayList<Pessoa> pessoas = new ArrayList<>();

                    for (int i = 0; i < 2; i++) {
                        pessoas.add(new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                               "ataWorld", "ataBrasil", false, faixaRepository.getOne(1), endereco,
                                               instrutor));
                    }

                    pessoas = (ArrayList<Pessoa>) pessoaDAO.saveAll(pessoas);

                    ArrayList<Competidor> competidorArrayList = new ArrayList<>(competidorRepository.findAll());

                    competidorArrayList.clear();
                    for (Pessoa pessoa : pessoas) {
                        competidorArrayList.add(
                                new Competidor(1.0, 1.0, "nivel", pessoa, categoriaCompeticaoArrayList));
                    }

                    competidorArrayList = (ArrayList<Competidor>) tipoPessoaDAO.saveAll(competidorArrayList);

                    timeArrayList.add(timeDAO.save(new Time("reprensenta", false, competidorArrayList)));
                    timeArrayList.add(timeDAO.save(new Time("reprensenta", true, competidorArrayList)));
                }
                ringueService.createRingueTime(timeArrayList, false, torneio, categoriaCompeticaoArrayList.get(0));
            }

            System.out.println("TERMINOU");

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

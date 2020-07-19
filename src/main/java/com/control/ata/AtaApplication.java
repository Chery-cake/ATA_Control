package com.control.ata;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.dao.RingueDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.model.endereco.Academia;
import com.control.ata.model.endereco.Bairro;
import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.individual.*;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.*;
import com.control.ata.repository.endereco.AcademiaRepository;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.individual.ChaveLutaIndividualRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
import com.control.ata.repository.torneio.CategoriaTorneioRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import com.control.ata.service.PopulateBD;
import com.control.ata.service.planilhaIndividual.ChaveIndividual;
import com.control.ata.service.planilhaIndividual.ListaIndividual;
import com.control.ata.service.planilhaIndividual.RankIndividual;
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
    private RingueDAO ringueDAO;
    @Autowired
    private AcademiaRepository academiaRepository;
    @Autowired
    private ListaIndividual listaIndividual;
    @Autowired
    private ChaveIndividual chaveIndividual;
    @Autowired
    private ChaveLutaIndividualRepository chaveLutaIndividualRepository;

    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;
    @Autowired
    private EnderecoDAO enderecoDAO;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private RankIndividual rankingIndividual;

    public static void main(String[] args) {
        SpringApplication.run(AtaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        populateBD.populate();
        Singleton s = Singleton.getSingleton();

        try {

            Endereco endereco = new Endereco("rua", new Bairro("bairro", cidadeRepository.getOne(1)));
            endereco = enderecoDAO.save(endereco);

            Torneio torneio = new Torneio(new Date(), new Date(), endereco, categoriaTorneioRepository.getOne(5));
            torneioRepository.save(torneio);

            CategoriaCompeticao categoriaCompeticao = new CategoriaCompeticao("nome", false, false, 1, 1, 1, 1, 1);
            categoriaCompeticao = categoriaCompeticaoRepository.save(categoriaCompeticao);
            ArrayList<CategoriaCompeticao> categoriaCompeticaoArrayList = new ArrayList<>();
            categoriaCompeticaoArrayList.add(categoriaCompeticao);

            CategoriaTorneio categoriaTorneio = categoriaTorneioRepository.getOne(3);
            CategoriaTorneio categoriaTorneio1 = categoriaTorneioRepository.getOne(5);
            CategoriaTorneio categoriaTorneio2 = categoriaTorneioRepository.getOne(2);
            CategoriaTorneio categoriaTorneio3 = categoriaTorneioRepository.getOne(1);

            Pessoa pessoaIns = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                          "ataWorld", "ataBrasil", true, faixaRepository.getOne(1), endereco);
            Pessoa pessoaIns1 = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                           "ataWorld", "ataBrasil", true, faixaRepository.getOne(1), endereco);

            pessoaIns = pessoaDAO.save(pessoaIns);
            pessoaIns1 = pessoaDAO.save(pessoaIns1);

            Academia academia = new Academia("academia", endereco);
            Academia academia1 = new Academia("academia1", endereco);

            academia = academiaRepository.save(academia);
            academia1 = academiaRepository.save(academia1);

            Instrutor instrutor = new Instrutor(academia, pessoaIns);
            Instrutor instrutor1 = new Instrutor(academia1, pessoaIns1);

            ArrayList<Instrutor> instrutorArrayList = new ArrayList<>();
            instrutorArrayList.add(instrutor);
            instrutorArrayList.add(instrutor1);

            instrutorArrayList = (ArrayList<Instrutor>) tipoPessoaDAO.saveAll(instrutorArrayList);

            instrutor = instrutorArrayList.get(0);
            instrutor1 = instrutorArrayList.get(1);

            Pessoa pessoa = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                       "ataWorld", "ataBrasil", false, faixaRepository.getOne(1), endereco, instrutor);
            Pessoa pessoa1 = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                        "ataWorld", "ataBrasil", false, faixaRepository.getOne(1), endereco, instrutor);
            Pessoa pessoa2 = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                        "ataWorld", "ataBrasil", false, faixaRepository.getOne(1), endereco,
                                        instrutor);
            Pessoa pessoa3 = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                        "ataWorld", "ataBrasil", false, faixaRepository.getOne(1), endereco,
                                        instrutor1);

            ArrayList<Pessoa> pessoas = new ArrayList<>();
            pessoas.add(pessoa);
            pessoas.add(pessoa1);
            pessoas.add(pessoa2);
            pessoas.add(pessoa3);

            pessoas = (ArrayList<Pessoa>) pessoaDAO.saveAll(pessoas);

            pessoa = pessoas.get(0);
            pessoa1 = pessoas.get(1);
            pessoa2 = pessoas.get(2);
            pessoa3 = pessoas.get(3);

            Juiz juiz = new Juiz(pessoa);
            Juiz juiz1 = new Juiz(pessoa1);
            Juiz juiz2 = new Juiz(pessoa2);

            RodadaJuiz rodadaJuiz = new RodadaJuiz(torneio, juiz);
            ArrayList<RodadaJuiz> rodadaJuizArrayList = new ArrayList<>();
            rodadaJuizArrayList.add(rodadaJuiz);

            RodadaJuiz rodadaJuiz1 = new RodadaJuiz(torneio, juiz1);
            ArrayList<RodadaJuiz> rodadaJuizArrayList1 = new ArrayList<>();
            rodadaJuizArrayList1.add(rodadaJuiz1);

            RodadaJuiz rodadaJuiz2 = new RodadaJuiz(torneio, juiz2);
            ArrayList<RodadaJuiz> rodadaJuizArrayList2 = new ArrayList<>();
            rodadaJuizArrayList2.add(rodadaJuiz2);

            juiz.setRodadaJuizList(rodadaJuizArrayList);
            juiz1.setRodadaJuizList(rodadaJuizArrayList1);
            juiz2.setRodadaJuizList(rodadaJuizArrayList2);

            ArrayList<Juiz> juizArrayList = new ArrayList<>();
            juizArrayList.add(juiz);
            juizArrayList.add(juiz1);
            juizArrayList.add(juiz2);

            juizArrayList = (ArrayList<Juiz>) tipoPessoaDAO.saveAll(juizArrayList);

            Competidor competidor = new Competidor(1.0, 1.0, "nivel", pessoa, categoriaCompeticaoArrayList);
            Competidor competidor1 = new Competidor(1.0, 1.0, "nivel", pessoa1, categoriaCompeticaoArrayList);
            Competidor competidor2 = new Competidor(1.0, 1.0, "nivel", pessoa2, categoriaCompeticaoArrayList);
            Competidor competidor3 = new Competidor(1.0, 1.0, "nivel", pessoa3, categoriaCompeticaoArrayList);

            ArrayList<Titulo> titulos = new ArrayList<>();
            Titulo titulo = new Titulo(2020, categoriaCompeticao, categoriaTorneio, competidor);
            titulos.add(titulo);

            ArrayList<Titulo> titulos1 = new ArrayList<>();
            Titulo titulo1 = new Titulo(2019, categoriaCompeticao, categoriaTorneio1, competidor1);
            titulos1.add(titulo1);

            ArrayList<Titulo> titulos2 = new ArrayList<>();
            Titulo titulo2 = new Titulo(2020, categoriaCompeticao, categoriaTorneio2, competidor2);
            titulos2.add(titulo2);

            ArrayList<Titulo> titulos3 = new ArrayList<>();
            Titulo titulo3 = new Titulo(2020, categoriaCompeticao, categoriaTorneio3, competidor3);
            titulos3.add(titulo3);

            competidor.setTituloList(titulos);
            competidor1.setTituloList(titulos1);
            competidor2.setTituloList(titulos2);
            competidor3.setTituloList(titulos3);

            ArrayList<Competidor> competidorArrayList = new ArrayList<>();
            competidorArrayList.add(competidor);
            competidorArrayList.add(competidor1);
            competidorArrayList.add(competidor2);
            competidorArrayList.add(competidor3);

            competidorArrayList = (ArrayList<Competidor>) tipoPessoaDAO.saveAll(competidorArrayList);

            RingueIndividual ringueIndividual = new RingueIndividual(false, 1, juizArrayList, competidorArrayList,
                                                                     torneio, categoriaCompeticaoArrayList);

            ringueIndividual = ringueDAO.save(ringueIndividual);

            PlanilhaListaIndividual planilhaListaIndividual = listaIndividual.createPlanilha(ringueIndividual,
                                                                                             categoriaCompeticao);

            for (ChaveListaIndividual chaveListaIndividual : planilhaListaIndividual.getChaveListaIndividualList()) {
                chaveListaIndividual.setNotaJuizA(s.getRandomInt(1, 10));
                chaveListaIndividual.setNotaJuizB(s.getRandomInt(1, 10));
                chaveListaIndividual.setNotaJuizC(s.getRandomInt(1, 10));
                listaIndividual.setChavePlanilha(chaveListaIndividual);
            }

            rankingIndividual.setRankingLista(planilhaListaIndividual);

            PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual = chaveIndividual.createPlanilha(
                    ringueIndividual, categoriaCompeticao);

            ArrayList<ChaveLutaIndividual> chaveLutaIndividualArrayList = new ArrayList<>(
                    chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividual(planilhaChaveamentoIndividual));

            for (ChaveLutaIndividual chaveLutaIndividual : chaveLutaIndividualArrayList) {
                chaveLutaIndividual.setDesqualificacaoVermelha(true);
                chaveIndividual.updateChave(chaveLutaIndividual);
            }

            ArrayList<ChaveLutaIndividual> chaveLutaIndividualArrayList1 = new ArrayList<>(
                    chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividual(planilhaChaveamentoIndividual));
            for (ChaveLutaIndividual chaveLutaIndividual : chaveLutaIndividualArrayList1) {
                if (!chaveLutaIndividual.getDesqualificacaoVermelha()) {
                    chaveLutaIndividual.setDesqualificacaoVermelha(true);
                    chaveIndividual.updateChave(chaveLutaIndividual);
                }
            }

            rankingIndividual.setRankingChave(planilhaChaveamentoIndividual);

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

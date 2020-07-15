package com.control.ata;

import com.control.ata.dao.*;
import com.control.ata.model.endereco.Bairro;
import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.*;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.tipo_pessoa.Treinador;
import com.control.ata.model.torneio.*;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.time.ChaveLutaTimeRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
import com.control.ata.repository.torneio.CategoriaTituloRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import com.control.ata.service.PopulateBD;
import com.control.ata.service.planilhaTime.ChaveTime;
import com.control.ata.service.planilhaTime.ListaTime;
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
    private FaixaRepository faixaRepository;
    @Autowired
    private CategoriaCompeticaoRepository categoriaCompeticaoRepository;
    @Autowired
    private CategoriaTituloRepository categoriaTituloRepository;
    @Autowired
    private RingueDAO ringueDAO;
    @Autowired
    private ListaTime listaTime;
    @Autowired
    private ChaveTime chaveTime;
    @Autowired
    private ChaveLutaTimeRepository chaveLutaTimeRepository;

    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;
    @Autowired
    private EnderecoDAO enderecoDAO;
    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private PopulateBD populateBD;
    @Autowired
    private TimeDAO timeDAO;

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

            Torneio torneio = new Torneio(new Date(), new Date(), endereco);
            torneioRepository.save(torneio);

            CategoriaCompeticao categoriaCompeticao = new CategoriaCompeticao("nome", false, false, 1, 1, 1, 1, 1);
            categoriaCompeticao = categoriaCompeticaoRepository.save(categoriaCompeticao);
            ArrayList<CategoriaCompeticao> categoriaCompeticaoArrayList = new ArrayList<>();
            categoriaCompeticaoArrayList.add(categoriaCompeticao);

            CategoriaTitulo categoriaTitulo = categoriaTituloRepository.getOne(3);
            CategoriaTitulo categoriaTitulo1 = categoriaTituloRepository.getOne(5);
            CategoriaTitulo categoriaTitulo2 = categoriaTituloRepository.getOne(2);
            CategoriaTitulo categoriaTitulo3 = categoriaTituloRepository.getOne(1);

            Pessoa pessoa = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                       "ataWorld",
                                       "ataBrasil", false, faixaRepository.getOne(1), endereco);
            Pessoa pessoa1 = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                        "ataWorld",
                                        "ataBrasil", false, faixaRepository.getOne(1), endereco);
            Pessoa pessoa2 = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                        "ataWorld",
                                        "ataBrasil", false, faixaRepository.getOne(1), endereco);
            Pessoa pessoa3 = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto",
                                        "ataWorld",
                                        "ataBrasil", false, faixaRepository.getOne(1), endereco);

            ArrayList<Pessoa> pessoas = new ArrayList<>();
            pessoas.add(pessoa);
            pessoas.add(pessoa1);
            pessoas.add(pessoa2);
            pessoas.add(pessoa3);

            pessoas = (ArrayList<Pessoa>) pessoaDAO.saveAll(pessoas);
//            pessoa = pessoaDAO.save(pessoa);

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

//            juiz = tipoPessoaDAO.save(juiz);
            juizArrayList = (ArrayList<Juiz>) tipoPessoaDAO.saveAll(juizArrayList);

            Competidor competidor = new Competidor(1.0, 1.0, "nivel", pessoa, categoriaCompeticaoArrayList);
            Competidor competidor1 = new Competidor(1.0, 1.0, "nivel", pessoa1, categoriaCompeticaoArrayList);
            Competidor competidor2 = new Competidor(1.0, 1.0, "nivel", pessoa2, categoriaCompeticaoArrayList);
            Competidor competidor3 = new Competidor(1.0, 1.0, "nivel", pessoa3, categoriaCompeticaoArrayList);

            ArrayList<Competidor> competidorArrayList = new ArrayList<>();
            competidorArrayList.add(competidor);
            competidorArrayList.add(competidor1);
            competidorArrayList.add(competidor2);
            competidorArrayList.add(competidor3);

            competidorArrayList = (ArrayList<Competidor>) tipoPessoaDAO.saveAll(competidorArrayList);
//            competidor = tipoPessoaDAO.save(competidor);

            Time time = new Time("representa", false, competidorArrayList);
            Time time1 = new Time("representa", false, competidorArrayList);
            Time time2 = new Time("representa", false, competidorArrayList);
            Time time3 = new Time("representa", false, competidorArrayList);

            ArrayList<Titulo> titulos = new ArrayList<>();
            Titulo titulo = new Titulo(2020, categoriaCompeticao, categoriaTitulo, time);
            titulos.add(titulo);

            time.setTituloList(titulos);

            ArrayList<Treinador> treinadorArrayList = new ArrayList<>();
            Treinador treinador = new Treinador(pessoa, time);
            treinadorArrayList.add(treinador);

            time.setTreinadorList(treinadorArrayList);

            ArrayList<Time> timeArrayList = new ArrayList<>();
            timeArrayList.add(time);
            timeArrayList.add(time1);
            timeArrayList.add(time2);
            timeArrayList.add(time3);

            timeArrayList = (ArrayList<Time>) timeDAO.saveAll(timeArrayList);

            RingueTime ringueTime = new RingueTime(false, 1, juizArrayList, timeArrayList, torneio,
                                                   categoriaCompeticaoArrayList);

            ringueTime = ringueDAO.save(ringueTime);

            ArrayList<PlanilhaListaTime> planilhaListaTimes = (ArrayList<PlanilhaListaTime>) listaTime.createPlanilha(
                    ringueTime, categoriaCompeticao);

            PlanilhaChaveamentoTime planilhaChaveamentoTime = chaveTime.createPlanilha(ringueTime, categoriaCompeticao);

            ArrayList<ChaveLutaTime> chaveLutaTimeArrayList = new ArrayList<>(chaveLutaTimeRepository.getAllByPlanilhaChaveamentoTime(planilhaChaveamentoTime));

            for (ChaveLutaTime chaveLutaTime:chaveLutaTimeArrayList){
                chaveLutaTime.setDesqualificacaoVermelha(true);
                chaveTime.updateChave(chaveLutaTime);
            }

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

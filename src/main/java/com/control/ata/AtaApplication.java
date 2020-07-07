package com.control.ata;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.dao.RingueDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.model.endereco.Bairro;
import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.individual.ChaveLutaIndividual;
import com.control.ata.model.individual.PlanilhaChaveamentoIndividual;
import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.model.torneio.Torneio;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.individual.ChaveLutaIndividualRepository;
import com.control.ata.repository.individual.RingueIndividualRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.tipo_pessoa.CompetidorRepository;
import com.control.ata.repository.tipo_pessoa.JuizRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import com.control.ata.service.PopulateBD;
import com.control.ata.service.planilhaIndividual.ChaveIndividual;
import com.control.ata.service.planilhaIndividual.ListaIndividual;
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
    private ChaveLutaIndividualRepository chaveLutaIndividualRepository;
    @Autowired
    private CompetidorRepository competidorRepository;
    @Autowired
    private JuizRepository juizRepository;
    @Autowired
    private RingueIndividualRepository ringueIndividualRepository;

    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;
    @Autowired
    private EnderecoDAO enderecoDAO;
    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private PopulateBD populateBD;
    @Autowired
    private RingueDAO ringueDAO;
    @Autowired
    private ListaIndividual listaIndividual;
    @Autowired
    private ChaveIndividual chaveIndividual;

    public static void main(String[] args) {
        SpringApplication.run(AtaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        populateBD.populate();

        Endereco endereco = new Endereco("rua", new Bairro("bairro", cidadeRepository.getOne(1)));
        try {
            endereco = enderecoDAO.save(endereco);
        }catch (Exception e){
            System.out.println(e);
        }

        Torneio torneio = new Torneio(new Date(), new Date(), endereco);
        try {
            torneioRepository.save(torneio);
        } catch (Exception e) {
            System.out.println(e);
        }

        Pessoa pessoa = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto", "ataWorld",
                                   "ataBrasil", false, faixaRepository.getOne(1), endereco);
        Pessoa pessoa1 = new Pessoa("nome", "sobrenome", false, new Date(), "usuario", "senha", 1, "foto", "ataWorld",
                                    "ataBrasil", false, faixaRepository.getOne(1), endereco);
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(pessoa);
        pessoas.add(pessoa1);
        try {
            pessoas = (ArrayList<Pessoa>) pessoaDAO.saveAll(pessoas);
            pessoa = pessoaDAO.save(pessoa);
        } catch (Exception e) {
            System.out.println(e);
        }
        pessoa = pessoas.get(0);
        pessoa1 = pessoas.get(1);

        Juiz juiz = new Juiz(pessoa);
        Juiz juiz1 = new Juiz(pessoa1);

        RodadaJuiz rodadaJuiz = new RodadaJuiz(torneio, juiz);
        ArrayList<RodadaJuiz> rodadaJuizArrayList = new ArrayList<>();
        rodadaJuizArrayList.add(rodadaJuiz);

        RodadaJuiz rodadaJuiz1 = new RodadaJuiz(torneio, juiz1);
        ArrayList<RodadaJuiz> rodadaJuizArrayList1 = new ArrayList<>();
        rodadaJuizArrayList1.add(rodadaJuiz1);

        juiz.setRodadaJuizList(rodadaJuizArrayList);
        juiz1.setRodadaJuizList(rodadaJuizArrayList1);

        ArrayList<Juiz> juizArrayList = new ArrayList<>();
        juizArrayList.add(juiz);
        juizArrayList.add(juiz1);
        try {
//            juiz = tipoPessoaDAO.save(juiz);
            juizArrayList = (ArrayList<Juiz>) tipoPessoaDAO.saveAll(juizArrayList);
        } catch (Exception e) {
            System.out.println(e);
        }

        CategoriaCompeticao categoriaCompeticao = new CategoriaCompeticao("nome", false, false, 1, 1, 1, 1, 1);
        categoriaCompeticaoRepository.save(categoriaCompeticao);
        ArrayList<CategoriaCompeticao> categoriaCompeticaoArrayList = new ArrayList<>();
        categoriaCompeticaoArrayList.add(categoriaCompeticao);

        Competidor competidor = new Competidor(1.0, 1.0, "nivel", pessoa, categoriaCompeticaoArrayList);
        Competidor competidor1 = new Competidor(1.0, 1.0, "nivel", pessoa1, categoriaCompeticaoArrayList);
        ArrayList<Competidor> competidorArrayList = new ArrayList<>();
        competidorArrayList.add(competidor);
        competidorArrayList.add(competidor1);

        try {
            competidorArrayList = (ArrayList<Competidor>) tipoPessoaDAO.saveAll(competidorArrayList);
//            competidor = tipoPessoaDAO.save(competidor);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            ringueIndividualRepository.deleteAll();
        }catch (Exception e){
            System.out.println(e);
        }

        RingueIndividual ringueIndividual = new RingueIndividual(false, 1, juizArrayList, competidorArrayList, torneio,
                                                                 categoriaCompeticaoArrayList);
        try {
            ringueIndividual = ringueDAO.save(ringueIndividual);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            ArrayList<PlanilhaListaIndividual> list = (ArrayList<PlanilhaListaIndividual>) listaIndividual.createPlanilha(
                    ringueIndividual, categoriaCompeticao);
            for (PlanilhaListaIndividual planilhaListaIndividual : list) {
                planilhaListaIndividual.setNotaJuizA(5);
                planilhaListaIndividual.setNotaJuizB(5);
                planilhaListaIndividual.setNotaJuizC(5);
                listaIndividual.setPlanilha(planilhaListaIndividual);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            PlanilhaChaveamentoIndividual planilha = chaveIndividual.createPlanilha(ringueIndividual,
                                                                                    categoriaCompeticao);
            ArrayList<ChaveLutaIndividual> chaveLutaIndividual = (ArrayList<ChaveLutaIndividual>) chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividual(planilha);
            for (ChaveLutaIndividual chave : chaveLutaIndividual) {
                System.out.println(chave);
                chave.setDesqualificacaoBranca(true);
                chave.setDesqualificacaoVermelha(true);
                chaveIndividual.updateChave(chave);
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

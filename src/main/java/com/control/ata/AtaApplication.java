package com.control.ata;

import com.control.ata.dao.RingueDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.model.endereco.Academia;
import com.control.ata.model.endereco.Cidade;
import com.control.ata.model.endereco.Estado;
import com.control.ata.model.endereco.Pais;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.control.ata.model.torneio.CategoriaTorneio;
import com.control.ata.repository.endereco.AcademiaRepository;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.pessoa.PessoaRepository;
import com.control.ata.repository.pessoa.PlanilheiroRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
import com.control.ata.repository.torneio.CategoriaTorneioRepository;
import com.control.ata.repository.torneio.RodadaJuizRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import com.control.ata.security.entity.Usuario;
import com.control.ata.security.enuns.UserRole;
import com.control.ata.security.repository.UsuarioRepository;
import com.control.ata.security.service.BCrypt;
import com.control.ata.security.service.UsuarioService;
import com.control.ata.service.RingueService;
import com.control.ata.service.planilhaIndividual.ChaveIndividual;
import com.control.ata.service.planilhaIndividual.ListaIndividual;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@ImportResource(locations = "classpath:dwr-spring.xml")
@SpringBootApplication
public class AtaApplication implements CommandLineRunner {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    //todo remover

    @Autowired
    private CategoriaCompeticaoRepository categoriaCompeticaoRepository;
    @Autowired
    private TorneioRepository torneioRepository;
    @Autowired
    private RingueDAO ringueDAO;
    @Autowired
    private RingueService ringueService;
    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;
    @Autowired
    private RodadaJuizRepository rodadaJuizRepository;
    @Autowired
    private ChaveIndividual chaveIndividual;
    @Autowired
    private ListaIndividual listaIndividual;
    @Autowired
    private PlanilheiroRepository planilheiroRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AcademiaRepository academiaRepository;

    public static void main(String[] args) {
        SpringApplication.run(AtaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Singleton.getSingleton();

        Pessoa pessoa = new Pessoa("ADMIN", "", false, new Date(), 0,
                                   "NumberWorld", "NumberBrasil", false,
                                   "telefone", null, null, null);

        pessoaRepository.save(pessoa);

        Usuario usuario = new Usuario(pessoa, "email", "root");
        usuario.setUserRole(UserRole.ROLE_ADMIN);
        usuario.setPassword(BCrypt.gerarBCrypt(usuario.getPassword()));
        usuario.setEnabled(true);

        usuarioRepository.save(usuario);

        pessoa.setUsuario(usuario);

        pessoaRepository.save(pessoa);

        //todo remover

        ArrayList<Pessoa> pessoaArrayList = new ArrayList<>();

        Pessoa pessoaInstru = pessoaRepository.save(new Pessoa("instrutor", "pessoa", false,// genero false = menina
                                                               new GregorianCalendar(2013, Calendar.FEBRUARY,
                                                                                     11).getTime(), 0, "NumberWorld",
                                                               "NumberBrasil", true, "telefone", null, null, null));

        Usuario usuario1 = new Usuario(pessoaInstru, "ins", "senha");
        usuario1.setUserRole(UserRole.ROLE_USER);
        usuario1.setPassword(BCrypt.gerarBCrypt(usuario1.getPassword()));
        usuario1.setEnabled(true);

        usuarioRepository.save(usuario1);

        pessoaInstru.setUsuario(usuario1);

        pessoaInstru = pessoaRepository.save(pessoaInstru);

        Academia academia = academiaRepository.save(new Academia("academia", null));

        Instrutor instrutor = tipoPessoaDAO.save(new Instrutor(academia, pessoaInstru));

        for (int i = 0; i < 4; i++) {
            pessoaArrayList.add(
                    pessoaRepository.save(new Pessoa(String.valueOf(i), "pessoa", false,// genero false = menina
                                                     new GregorianCalendar(2013, Calendar.FEBRUARY,
                                                                           11).getTime(), 0, "NumberWorld",
                                                     "NumberBrasil", false, "telefone", null, null, null)));
        }

        for (Pessoa pessoa1 : pessoaArrayList) {
            pessoa1.setInstrutor(instrutor);
            pessoa1 = pessoaRepository.save(pessoa1);
        }

        System.out.println("Terminou insercoes");

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

    @Bean
    CommandLineRunner runner(PaisRepository paisRepository, EstadoRepository estadoRepository,
            CidadeRepository cidadeRepository, FaixaRepository faixaRepository,
            CategoriaTorneioRepository categoriaTorneioRepository) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<pais>> typeReferenceAddress = new TypeReference<List<pais>>() {
            };
            InputStream inputStreamAddress = TypeReference.class.getResourceAsStream("/json/endereco.json");
            try {
                List<pais> pais = mapper.readValue(inputStreamAddress, typeReferenceAddress);
                for (AtaApplication.pais pais1 : pais) {
                    Pais pais2 = paisRepository.save(new Pais(pais1.nome));
                    for (estados estados : pais1.estados) {
                        Estado estado = estadoRepository.save(
                                new Estado(estados.estado.nome, estados.estado.sigla, pais2));
                        for (String cidade : estados.estado.cidades) {
                            cidadeRepository.save(new Cidade(cidade, estado));
                        }
                    }
                }
                System.out.println("Address Saved!");

            } catch (IOException e) {
                System.out.println("Unable to save address: " + e.getMessage());
            }

            TypeReference<List<Faixa>> typeReferenceFaixa = new TypeReference<List<Faixa>>() {
            };
            InputStream inputStreamFaixa = TypeReference.class.getResourceAsStream("/json/faixa.json");

            try {
                List<Faixa> faixas = mapper.readValue(inputStreamFaixa, typeReferenceFaixa);

                faixaRepository.saveAll(faixas);

                System.out.println("Faixas Saved!");

            } catch (IOException e) {
                System.out.println("Unable to save faixas: " + e.getMessage());
            }

            TypeReference<List<CategoriaTorneio>> typeReferenceCatTor = new TypeReference<List<CategoriaTorneio>>() {
            };
            InputStream inputStreamCatTor = TypeReference.class.getResourceAsStream("/json/categoriaTorneio.json");

            try {
                List<CategoriaTorneio> categoriaTorneios = mapper.readValue(inputStreamCatTor, typeReferenceCatTor);

                categoriaTorneioRepository.saveAll(categoriaTorneios);

                System.out.println("Categorias Torneio Saved!");

            } catch (IOException e) {
                System.out.println("Unable to save categorias Torneio: " + e.getMessage());
            }
        };
    }

    private static class pais {

        public String nome;
        public ArrayList<estados> estados;
    }

    private static class estados {
        public estado estado;
    }

    private static class estado {
        public String sigla;
        public String nome;
        public ArrayList<String> cidades;
    }

}
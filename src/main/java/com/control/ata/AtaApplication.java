package com.control.ata;

import com.control.ata.dao.RingueDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.model.endereco.Academia;
import com.control.ata.model.endereco.Cidade;
import com.control.ata.model.endereco.Estado;
import com.control.ata.model.endereco.Pais;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.pessoa.Planilheiro;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.CategoriaTorneio;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.model.torneio.Torneio;
import com.control.ata.repository.endereco.AcademiaRepository;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.individual.ChaveListaIndividualRepository;
import com.control.ata.repository.individual.ChaveLutaIndividualRepository;
import com.control.ata.repository.individual.PlanilhaChaveamentoIndividualRepository;
import com.control.ata.repository.individual.PlanilhaListaIndividualRepository;
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
import com.control.ata.security.service.EmailSenderService;
import com.control.ata.security.service.UsuarioService;
import com.control.ata.service.RingueService;
import com.control.ata.service.planilhaIndividual.ChaveIndividual;
import com.control.ata.service.planilhaIndividual.ListaIndividual;
import com.control.ata.service.planilhaIndividual.RankIndividual;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
    @Autowired
    private ChaveListaIndividualRepository chaveListaIndividualRepository;
    @Autowired
    private ChaveLutaIndividualRepository chaveLutaIndividualRepository;
    @Autowired
    private RankIndividual rankIndividual;
    @Autowired
    private CategoriaTorneioRepository categoriaTorneioRepository;
    @Autowired
    private PlanilhaChaveamentoIndividualRepository planilhaChaveamentoIndividualRepository;
    @Autowired
    private PlanilhaListaIndividualRepository planilhaListaIndividualRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    public static void main(String[] args) {
        SpringApplication.run(AtaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Singleton.getSingleton();

        if (usuarioRepository.getUsuarioByUserRole(UserRole.ROLE_ADMIN) == null) {
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
        }

        //todo remover

//        ArrayList<CategoriaCompeticao> competicaoArrayList = new ArrayList<>();
//        competicaoArrayList.add(
//                categoriaCompeticaoRepository.save(new CategoriaCompeticao("lista", false, false, 0, 0, 0, 0, 0)));
//        competicaoArrayList.add(
//                categoriaCompeticaoRepository.save(new CategoriaCompeticao("chave", true, false, 3, 2, 0, 0, 0)));
//
//        CategoriaTorneio categoriaTorneio = categoriaTorneioRepository.save(new CategoriaTorneio("nome", 5));
//
//        Torneio torneio = torneioRepository.save(new Torneio(new Date(), new Date(), 1, false, null, categoriaTorneio));
//        RodadaJuiz rodadaJuiz = rodadaJuizRepository.save(new RodadaJuiz("ini", "ter", new Date(), torneio));
//
//        Planilheiro planilheiro = planilheiroRepository.save(new Planilheiro(torneio));
//
//        Usuario usuario1 = new Usuario(planilheiro, "ema", "root");
//        usuario1.setUserRole(UserRole.ROLE_PLANILHA);
//        usuarioService.signUpUser(usuario1);
//
//        ArrayList<Pessoa> pessoaArrayList = new ArrayList<>();
//
//        Pessoa pessoaInstru = pessoaRepository.save(new Pessoa("instrutor", "pessoa", false,// genero false = menina
//                new GregorianCalendar(2013, Calendar.FEBRUARY,
//                        11).getTime(), 0, "NumberWorld",
//                "NumberBrasil", false, "telefone", null, null, null));
//
//        Academia academia = academiaRepository.save(new Academia("academia", null));
//
//        Instrutor instrutor = tipoPessoaDAO.save(new Instrutor(academia, pessoaInstru));
//
//        for (int i = 0; i < 8; i++) {
//            pessoaArrayList.add(
//                    pessoaRepository.save(new Pessoa(String.valueOf(i), "pessoa", false,// genero false = menina
//                            new GregorianCalendar(2013, Calendar.FEBRUARY,
//                                    11).getTime(), 0, "NumberWorld",
//                            "NumberBrasil", false, "telefone", null, null, null)));
//        }
//
//        for (Pessoa pessoa1 : pessoaArrayList) {
//            pessoa1.setInstrutor(instrutor);
//            pessoa1 = pessoaRepository.save(pessoa1);
//            tipoPessoaDAO.save(new Competidor(55d, 55d, 0, pessoa1, torneio, competicaoArrayList));
//        }
//
//        pessoaArrayList = new ArrayList<>();
//
//        for (int i = 0; i < 3; i++) {
//            pessoaArrayList.add(
//                    pessoaRepository.save(new Pessoa(String.valueOf(i), "juiz", false,// genero false = menina
//                            new GregorianCalendar(2013, Calendar.FEBRUARY,
//                                    11).getTime(), 0, "NumberWorld",
//                            "NumberBrasil", false, "telefone", null, null, null)));
//        }
//
//        ArrayList<RodadaJuiz> rodadaJuizArrayList = new ArrayList<>();
//        rodadaJuizArrayList.add(rodadaJuiz);
//
//        ArrayList<Juiz> juizArrayList = new ArrayList<>();
//
//        for (Pessoa pessoa1 : pessoaArrayList) {
//            pessoa1 = pessoaRepository.save(pessoa1);
//            juizArrayList.add(tipoPessoaDAO.save(new Juiz(pessoa1, rodadaJuizArrayList)));
//        }
//
//        ArrayList<RingueIndividual> ringueIndividualArrayList = new ArrayList<>();
//
//        for (int i = 1; i <= 2; i++) {
//            ringueIndividualArrayList.add(ringueDAO.save(
//                    new RingueIndividual(false, false, 1, i, 1, 0, juizArrayList, torneio, competicaoArrayList, rodadaJuiz)));
//        }
//
//        ringueService.createRingueIndividual(torneio);
//
//        Singleton s = Singleton.getSingleton();
//
//        for (RingueIndividual ringueIndividual : ringueIndividualArrayList) {
//            listaIndividual.createPlanilhasLista(ringueIndividual);
//            chaveIndividual.createPlanilhasChave(ringueIndividual);

//            for (PlanilhaListaIndividual planilhaListaIndividual : planilhaListaIndividualRepository.getAllByRingueIndividual(ringueIndividual)) {
//                for (ChaveListaIndividual chaveListaIndividual : chaveListaIndividualRepository.getAllByPlanilhaListaIndividual(planilhaListaIndividual)) {
//                    chaveListaIndividual.setNotaJuizA(s.getRandomInt(1, 10));
//                    chaveListaIndividual.setNotaJuizB(s.getRandomInt(1, 10));
//                    chaveListaIndividual.setNotaJuizC(s.getRandomInt(1, 10));
//                    chaveListaIndividualRepository.save(chaveListaIndividual);
//                }
//                rankIndividual.setRankingLista(planilhaListaIndividual);
//            }
//
//            for (PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual : planilhaChaveamentoIndividualRepository.getAllByRingueIndividual(ringueIndividual)) {
//
//                ArrayList<ChaveLutaIndividual> chaveLutaIndividualArrayList = (ArrayList<ChaveLutaIndividual>) chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividual(
//                        planilhaChaveamentoIndividual);
//                ArrayList<ChaveLutaIndividual> chaveLutaIndividualArrayListRemoved = new ArrayList<>();
//
//                for (int i = chaveLutaIndividualArrayList.get(0).getFase(); i >= 0; i--) {
//
//                    for (ChaveLutaIndividual chaveLutaIndividual : chaveLutaIndividualArrayList) {
//                        chaveLutaIndividual.setDesqualificacaoVermelha(true);
//                        chaveLutaIndividual.setDesqualificacaoBranca(false);
//                        chaveLutaIndividualRepository.save(chaveLutaIndividual);
//                        chaveIndividual.updateChave(chaveLutaIndividual);
//                    }
//
//                    ArrayList<ChaveLutaIndividual> chaveLutaIndividualArrayList1 = (ArrayList<ChaveLutaIndividual>) chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividual(
//                            planilhaChaveamentoIndividual);
//
//                    for (ChaveLutaIndividual chaveLutaIndividual : chaveLutaIndividualArrayList1) {
//                        for (ChaveLutaIndividual chaveLutaIndividual1 : chaveLutaIndividualArrayList) {
//                            if (chaveLutaIndividual.getId().equals(chaveLutaIndividual1.getId())) {
//                                chaveLutaIndividualArrayListRemoved.add(chaveLutaIndividual);
//                            }
//                        }
//                    }
//
//                    chaveLutaIndividualArrayList.clear();
//
//                    for (ChaveLutaIndividual chaveLutaIndividual : chaveLutaIndividualArrayList1) {
//                        if (!chaveLutaIndividualArrayListRemoved.contains(chaveLutaIndividual)) {
//                            chaveLutaIndividualArrayList.add(chaveLutaIndividual);
//                        }
//                    }
//
//                }
//
//                rankIndividual.setRankingChave(planilhaChaveamentoIndividual);
//            }
//        }

        System.out.println("Terminou insercoes");

//        emailSenderService.sendSimpleMessage("estudanteenem888@gmail.com", "test", "tesfasdfndjfshufbcjsak");

    }

    @Bean
    JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("ata.control.br@gmail.com");
        mailSender.setPassword("ATA@c0ntr0l");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
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
                    if (paisRepository.getPaisByNome(pais1.nome) == null) {
                        Pais pais2 = paisRepository.save(new Pais(pais1.nome));
                        for (estados estados : pais1.estados) {
                            Estado estado = estadoRepository.save(
                                    new Estado(estados.estado.nome, estados.estado.sigla, pais2));
                            for (String cidade : estados.estado.cidades) {
                                cidadeRepository.save(new Cidade(cidade, estado));
                            }
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

                for (Faixa faixa : faixas) {
                    if (faixaRepository.getFaixaByNome(faixa.getNome()) == null) {
                        faixaRepository.save(faixa);
                    }
                }

                System.out.println("Faixas Saved!");

            } catch (IOException e) {
                System.out.println("Unable to save faixas: " + e.getMessage());
            }

            TypeReference<List<CategoriaTorneio>> typeReferenceCatTor = new TypeReference<List<CategoriaTorneio>>() {
            };
            InputStream inputStreamCatTor = TypeReference.class.getResourceAsStream("/json/categoriaTorneio.json");

            try {
                List<CategoriaTorneio> categoriaTorneios = mapper.readValue(inputStreamCatTor, typeReferenceCatTor);

                for (CategoriaTorneio categoriaTorneio : categoriaTorneios) {
                    if (categoriaTorneioRepository.getCategoriaTorneioByNome(categoriaTorneio.getNome()) == null) {
                        categoriaTorneioRepository.save(categoriaTorneio);
                    }
                }

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
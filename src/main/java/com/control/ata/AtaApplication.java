package com.control.ata;

import com.control.ata.model.endereco.Cidade;
import com.control.ata.model.endereco.Estado;
import com.control.ata.model.endereco.Pais;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.CategoriaTorneio;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.pessoa.PessoaRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
import com.control.ata.repository.torneio.CategoriaTorneioRepository;
import com.control.ata.security.entity.Usuario;
import com.control.ata.security.enuns.UserRole;
import com.control.ata.security.repository.UsuarioRepository;
import com.control.ata.security.service.BCrypt;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ImportResource(locations = "classpath:dwr-spring.xml")
@SpringBootApplication
public class AtaApplication implements CommandLineRunner {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaCompeticaoRepository categoriaCompeticaoRepository;

    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private FaixaRepository faixaRepository;
    @Autowired
    private CategoriaTorneioRepository categoriaTorneioRepository;

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

        //todo remover
        categoriaCompeticaoRepository.save(new CategoriaCompeticao("nome", false, false, 0,
                                                                   0, 0, 0, 0));

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
    CommandLineRunner runner(PaisRepository paisRepository) {
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
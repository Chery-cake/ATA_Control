package com.control.ata;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.model.endereco.*;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.pessoa.Telefone;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.model.torneio.Torneio;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.torneio.RodadaJuizRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import org.directwebremoting.spring.DwrSpringServlet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@ImportResource(locations = "classpath:dwr-spring.xml")
@SpringBootApplication
public class AtaApplication implements CommandLineRunner {

    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private TorneioRepository torneioRepository;
    @Autowired
    private RodadaJuizRepository rodadaJuizRepository;
    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;

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
        this.addEndereco();


        Cidade cidade = cidadeRepository.getOne(1);

        Endereco endereco = new Endereco("rua", new Bairro("bairro", cidade));

        ArrayList<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);

        enderecoDAO.saveAll(enderecos);

        Torneio torneio = new Torneio(new Date(), new Date(), endereco);

        torneioRepository.save(torneio);

        Faixa faixa = new Faixa("faixa");
        faixaRepository.save(faixa);

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
        }catch (Exception e){
            System.out.println(e);
        }


        RodadaJuiz rodadaJuiz = new RodadaJuiz(torneio, juiz);

        rodadaJuizRepository.save(rodadaJuiz);

        try {
            pessoaDAO.deleteAll(pessoas);
//            tipoPessoaDAO.delete(juiz);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    private void addEndereco() {

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src\\main\\resources\\static\\endereco.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray paises = (JSONArray) obj;

            for (Object object : paises) {
                JSONObject pais = (JSONObject) object;
                Pais paisObj = new Pais((String) pais.get("nome"));
                paisRepository.save(paisObj);

                Object aux = pais.get("estados");
                JSONArray estados = (JSONArray) aux;
                for (Object o : estados) {
                    object = o;
                    JSONObject estado = (JSONObject) object;
                    object = estado.get("estado");
                    estado = (JSONObject) object;
                    Estado estadoObj = new Estado((String) estado.get("nome"), (String) estado.get("sigla"), paisObj);
                    estadoRepository.save(estadoObj);

                    Object aux2 = estado.get("cidades");
                    JSONArray cidades = (JSONArray) aux2;
                    for (Object value : cidades) {
                        String cidade = (String) value;
                        Cidade cidadeOBJ = new Cidade(cidade, estadoObj);
                        cidadeRepository.save(cidadeOBJ);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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

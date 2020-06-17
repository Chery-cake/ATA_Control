package com.control.ata;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.model.endereco.*;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.pessoa.Telefone;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
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

        Faixa faixa = new Faixa("faixa");
        faixaRepository.save(faixa);

        Pessoa pessoa = new Pessoa("nome", "sobrenome", false, new Date(), "usuario",
                "senha", 1, "foto", "world", "brasil", faixa, endereco);

        Telefone telefone = new Telefone("telefone", false, pessoa);

        ArrayList<Telefone> telefones = new ArrayList<>();

        telefones.add(telefone);
        telefones.add(new Telefone("telefone1", false, pessoa));
        telefones.add(new Telefone("telefone2", false, pessoa));

        pessoa.setTelefoneCollection(telefones);

        ArrayList<Pessoa> pessoas = new ArrayList<>();

        pessoas.add(pessoa);
        Pessoa pessoa1 = new Pessoa("nome1", "sobrenome", false, new Date(), "usuario",
                "senha", 1, "foto", "world", "brasil", faixa, endereco);
        telefones = new ArrayList<>();
        telefones.add(new Telefone("telefone1", false, pessoa1));
        telefones.add(new Telefone("telefone2", false, pessoa1));
        telefones.add(new Telefone("telefone3", false, pessoa1));
        pessoa1.setTelefoneCollection(telefones);
        pessoas.add(pessoa1);
        Pessoa pessoa2 = new Pessoa("nome2", "sobrenome", false, new Date(), "usuario",
                "senha", 1, "foto", "world", "brasil", faixa, endereco);
        telefones = new ArrayList<>();
        telefones.add(new Telefone("telefone1", false, pessoa2));
        telefones.add(new Telefone("telefone2", false, pessoa2));
        telefones.add(new Telefone("telefone3", false, pessoa2));
        pessoa2.setTelefoneCollection(telefones);
        pessoas.add(pessoa2);
        Pessoa pessoa3 = new Pessoa("nome3", "sobrenome", false, new Date(), "usuario",
                "senha", 1, "foto", "world", "brasil", faixa, endereco);
        telefones = new ArrayList<>();
        telefones.add(new Telefone("telefone1", false, pessoa3));
        telefones.add(new Telefone("telefone2", false, pessoa3));
        telefones.add(new Telefone("telefone3", false, pessoa3));
        pessoa3.setTelefoneCollection(telefones);
        pessoas.add(pessoa3);

        try {
            pessoaDAO.saveAll(pessoas);
        } catch (Exception e) {
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

package com.control.ata.service;

import com.control.ata.model.endereco.Cidade;
import com.control.ata.model.endereco.Estado;
import com.control.ata.model.endereco.Pais;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.torneio.CategoriaTorneio;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.torneio.CategoriaTorneioRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class PopulateBD {//todo criar method para inserir e criar JSON's

    private final JSONParser jsonParser = new JSONParser();
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

    public void populate() {
        this.addEndereco();
        this.addFaixa();
        this.addCategoriaTorneio();
    }

    public void criaJSON() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        FileWriter writeFile;

        //Armazena dados em um Objeto JSON
        jsonObject.put("nome", "Mundial");
        jsonObject.put("prioridade", 4);

        //Cria o parse de tratamento
        JSONParser parser = new JSONParser();

        try {
            //Salva no objeto JSONObject o que o parse tratou do arquivo
            jsonArray = (JSONArray) parser.parse(new FileReader(
                    "src\\main\\resources\\static\\arquivo.json"));

            jsonArray.add(jsonObject);

            System.out.println(jsonArray.toJSONString());
        }
        //Trata as exceptions que podem ser lançadas no decorrer do processo
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        try {
            writeFile = new FileWriter("src\\main\\resources\\static\\arquivo.json");
            //Escreve no arquivo conteudo do Objeto JSON
            writeFile.write(jsonArray.toJSONString());
            writeFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEndereco() {

//        jsonParser = new JSONParser();

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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addFaixa() {

//        jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src\\main\\resources\\static\\faixa.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray faixas = (JSONArray) obj;

            for (Object object : faixas) {
                JSONObject faixa = (JSONObject) object;
                Faixa faixaObj = new Faixa((String) faixa.get("nome"));
                faixaRepository.save(faixaObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addCategoriaTorneio() {

//        jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src\\main\\resources\\static\\categoriaTorneio.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray faixas = (JSONArray) obj;

            for (Object object : faixas) {
                JSONObject categoriaTorneio = (JSONObject) object;
                CategoriaTorneio categoriaTorneioObj = new CategoriaTorneio((String) categoriaTorneio.get("nome"),
                                                                           (int) (long) categoriaTorneio.get(
                                                                                 "prioridade"));
                categoriaTorneioRepository.save(categoriaTorneioObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

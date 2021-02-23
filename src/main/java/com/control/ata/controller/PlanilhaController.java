package com.control.ata.controller;

import com.control.ata.model.individual.ChaveListaIndividual;
import com.control.ata.model.individual.ChaveLutaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.torneio.Cronometro;
import com.control.ata.repository.individual.*;
import com.control.ata.repository.torneio.CronometroRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import com.control.ata.service.planilhaIndividual.ChaveIndividual;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PlanilhaController {

    @Autowired
    private RingueIndividualRepository ringueIndividualRepository;
    @Autowired
    private TorneioRepository torneioRepository;
    @Autowired
    private PlanilhaChaveamentoIndividualRepository planilhaChaveamentoIndividualRepository;
    @Autowired
    private PlanilhaListaIndividualRepository planilhaListaIndividualRepository;
    @Autowired
    private ChaveListaIndividualRepository chaveListaIndividualRepository;
    @Autowired
    private ChaveLutaIndividualRepository chaveLutaIndividualRepository;
    @Autowired
    private CronometroRepository cronometroRepository;
    @Autowired
    private ChaveIndividual chaveIndividual;

    // ======================================PLANILHA=============================================

    @GetMapping("/planilha")
    public String planilha() {
        return "planilha";
    }

    // ======================================MODEL ATTRIBUTES=============================================

    @PostMapping("/torneio/numero/ringues/individual/{id}")
    public ResponseEntity<?> getNumeroRingues(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(torneioRepository.getOne(id).getMaxNumeroRingues());
    }

    @PostMapping("/ringue/individual/lista/{numero}")
    public ResponseEntity<?> getNumeroListaRingues(@PathVariable("numero") Integer numero) {
        return ResponseEntity.ok(ringueIndividualRepository.getAllByNumeroRingueAndFinalizado(numero, false));
    }

    @PostMapping("/ringue/individual/lista/planilhas/lista/{id}")
    public ResponseEntity<?> getRinguePlanilhasLista(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(
                planilhaListaIndividualRepository.getAllByRingueIndividual(ringueIndividualRepository.getOne(id)));
    }

    @PostMapping("/ringue/individual/lista/planilhas/chave/{id}")
    public ResponseEntity<?> getRinguePlanilhasChaves(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(planilhaChaveamentoIndividualRepository.getAllByRingueIndividual(
                ringueIndividualRepository.getOne(id)));
    }

    @PostMapping("/ringue/individual/finalizar/{id}")
    public ResponseEntity<?> finalizaRingue(@PathVariable("id") Integer id) {
        RingueIndividual ringueIndividual = ringueIndividualRepository.getOne(id);
        ringueIndividual.setFinalizado(true);
        ringueIndividualRepository.save(ringueIndividual);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/planilha/individual/lista/competidores/{id}")
    public ResponseEntity<?> getCompetidoresPlanilhaLista(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chaveListaIndividualRepository.getAllByPlanilhaListaIndividual(
                planilhaListaIndividualRepository.getOne(id)));
    }

    @PostMapping("/chave/lista/individual/{id}")
    public ResponseEntity<?> setChavePlanilhaLista(@Valid @RequestBody String json, @PathVariable("id") Integer id,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        chaveListaIndividualDTO chaveListaIndividualDTO = jacksonObjectMapper.readValue(decodedJson,
                                                                                        chaveListaIndividualDTO.class);

        ChaveListaIndividual chaveListaIndividual = chaveListaIndividualRepository.getOne(id);

        chaveListaIndividual.setNotaJuizA(chaveListaIndividualDTO.nota_juiz_a);
        chaveListaIndividual.setNotaJuizB(chaveListaIndividualDTO.nota_juiz_b);
        chaveListaIndividual.setNotaJuizC(chaveListaIndividualDTO.nota_juiz_c);

        chaveListaIndividualRepository.save(chaveListaIndividual);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/planilha/individual/chave/competidores/{id}")
    public ResponseEntity<?> getCompetidoresPlanilhaChave(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividual(
                planilhaChaveamentoIndividualRepository.getOne(id)));
    }

    @PostMapping("/chave/luta/individual/{id}")
    public ResponseEntity<?> setChavePlanilhaChave(@Valid @RequestBody String json, @PathVariable("id") Integer id,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        chaveLutaIndividualDTO chaveLutaIndividualDTO = jacksonObjectMapper.readValue(decodedJson,
                                                                                      chaveLutaIndividualDTO.class);

        ChaveLutaIndividual chaveLutaIndividual = chaveLutaIndividualRepository.getOne(id);

        chaveLutaIndividual.setPontosVermelhos(chaveLutaIndividualDTO.pontos_vermelho);
        chaveLutaIndividual.setAdvertenciasVermelhas(chaveLutaIndividualDTO.advertencias_vermelhas);
        chaveLutaIndividual.setPenalidadesVermelhas(chaveLutaIndividualDTO.penalidades_vermelhas);
        chaveLutaIndividual.setPontosBrancos(chaveLutaIndividualDTO.pontos_brancos);
        chaveLutaIndividual.setAdvertenciasBrancas(chaveLutaIndividualDTO.advertencias_brancas);
        chaveLutaIndividual.setPenalidadesBrancas(chaveLutaIndividualDTO.penalidades_brancas);

        chaveLutaIndividualRepository.save(chaveLutaIndividual);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/chave/luta/individual/desqualificacao/{id}")
    public ResponseEntity<?> setChavePlanilhaChaveDesqualificacao(@Valid @RequestBody String json,
            @PathVariable("id") Integer id,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        chaveLutaIndividualDesqualificacaoDTO chaveLutaIndividualDesqualificacaoDTO = jacksonObjectMapper.readValue(
                decodedJson,
                chaveLutaIndividualDesqualificacaoDTO.class);

        ChaveLutaIndividual chaveLutaIndividual = chaveLutaIndividualRepository.getOne(id);

        chaveLutaIndividual.setDesqualificacaoVermelha(chaveLutaIndividualDesqualificacaoDTO.vermelha);
        chaveLutaIndividual.setDesqualificacaoBranca(chaveLutaIndividualDesqualificacaoDTO.branca);

        chaveLutaIndividualRepository.save(chaveLutaIndividual);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/planilha/individual/chave/fase/competidores")
    public ResponseEntity<?> getCompetidoresPlanilhaChaveFase(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        chaveLutaIndividualPlanilhaFaseDTO chaveLutaIndividualPlanilhaFaseDTO = jacksonObjectMapper.readValue(
                decodedJson,
                chaveLutaIndividualPlanilhaFaseDTO.class);


        return ResponseEntity.ok(chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividualAndFase(
                planilhaChaveamentoIndividualRepository.getOne(chaveLutaIndividualPlanilhaFaseDTO.id_plan),
                chaveLutaIndividualPlanilhaFaseDTO.fase));
    }

    @PostMapping("/chave/luta/individual/avancar/{id}")
    public ResponseEntity<?> setChavePlanilhaChaveDesqualificacao(@PathVariable("id") Integer id) {

        ChaveLutaIndividual chaveLutaIndividual = chaveLutaIndividualRepository.getOne(id);

        chaveIndividual.updateChave(chaveLutaIndividual);

        return ResponseEntity.ok(chaveLutaIndividual.getFase() - 1);
    }

    @PostMapping("/ringue/individual/cronometro/save/{id}")
    public ResponseEntity<?> setCronometro(@Valid @RequestBody String json, @PathVariable("id") Integer id,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        cronometroDTO cronometroDTO = jacksonObjectMapper.readValue(decodedJson,
                                                                    PlanilhaController.cronometroDTO.class);

        RingueIndividual ringueIndividual = ringueIndividualRepository.getOne(id);
        Cronometro cronometro = new Cronometro();

        if (cronometroRepository.getByRingueIndividual(ringueIndividual) != null) {
            cronometro = cronometroRepository.getByRingueIndividual(ringueIndividual);
        } else {
            cronometro.setRingueIndividual(ringueIndividual);
        }

        cronometro.setRodando(cronometroDTO.rodando);
        cronometro.setTempo_mim(cronometroDTO.tempo_mim);
        cronometro.setTempo_seg(cronometroDTO.tempo_seg);

        cronometroRepository.save(cronometro);

        return ResponseEntity.ok().build();
    }

    // ======================================FUNCTIONS=============================================

    private ResponseEntity<?> getErrors(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errors);
        }
        return null;
    }

    // ======================================CLASSES=============================================

    private static class chaveListaIndividualDTO {
        public Integer nota_juiz_a;
        public Integer nota_juiz_b;
        public Integer nota_juiz_c;

        public chaveListaIndividualDTO() {
        }

        public chaveListaIndividualDTO(Integer nota_juiz_a, Integer nota_juiz_b, Integer nota_juiz_c) {
            this.nota_juiz_a = nota_juiz_a;
            this.nota_juiz_b = nota_juiz_b;
            this.nota_juiz_c = nota_juiz_c;
        }
    }

    private static class chaveLutaIndividualDTO {
        public Integer pontos_vermelho;
        public Integer advertencias_vermelhas;
        public Integer penalidades_vermelhas;
        public Integer pontos_brancos;
        public Integer advertencias_brancas;
        public Integer penalidades_brancas;

        public chaveLutaIndividualDTO() {
        }

        public chaveLutaIndividualDTO(Integer pontos_vermelho, Integer advertencias_vermelhas,
                Integer penalidades_vermelhas, Integer pontos_brancos, Integer advertencias_brancas,
                Integer penalidades_brancas) {
            this.pontos_vermelho = pontos_vermelho;
            this.advertencias_vermelhas = advertencias_vermelhas;
            this.penalidades_vermelhas = penalidades_vermelhas;
            this.pontos_brancos = pontos_brancos;
            this.advertencias_brancas = advertencias_brancas;
            this.penalidades_brancas = penalidades_brancas;
        }
    }

    private static class chaveLutaIndividualDesqualificacaoDTO {
        public Boolean vermelha;
        public Boolean branca;

        public chaveLutaIndividualDesqualificacaoDTO() {
        }

        public chaveLutaIndividualDesqualificacaoDTO(Boolean vermelha, Boolean branca) {
            this.vermelha = vermelha;
            this.branca = branca;
        }
    }

    private static class chaveLutaIndividualPlanilhaFaseDTO {
        public Integer fase;
        public Integer id_plan;

        public chaveLutaIndividualPlanilhaFaseDTO() {
        }

        public chaveLutaIndividualPlanilhaFaseDTO(Integer fase, Integer id_plan) {
            this.fase = fase;
            this.id_plan = id_plan;
        }
    }

    private static class cronometroDTO {
        public Integer tempo_mim;
        public Integer tempo_seg;
        public Boolean rodando;

        public cronometroDTO() {
        }

        public cronometroDTO(Integer tempo_mim, Integer tempo_seg, Boolean rodando) {
            this.tempo_mim = tempo_mim;
            this.tempo_seg = tempo_seg;
            this.rodando = rodando;
        }
    }

}
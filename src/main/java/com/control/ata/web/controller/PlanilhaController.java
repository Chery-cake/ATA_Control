package com.control.ata.web.controller;

import com.control.ata.model.individual.ChaveListaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.repository.individual.*;
import com.control.ata.repository.torneio.TorneioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

//    @PostMapping("/planilha/individual/chave/competidores/{id}")
//    public ResponseEntity<?> getCompetidoresPlanilhaChave(@PathVariable("id") Integer id){
//        return ResponseEntity.ok(chaveLutaIndividualRepository.);
//    }

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

}
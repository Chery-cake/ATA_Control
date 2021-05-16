package com.control.ata.controller;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.torneio.Placar;
import com.control.ata.repository.individual.ChaveLutaIndividualRepository;
import com.control.ata.repository.individual.PlanilhaChaveamentoIndividualRepository;
import com.control.ata.repository.individual.PlanilhaListaIndividualRepository;
import com.control.ata.repository.individual.RingueIndividualRepository;
import com.control.ata.repository.torneio.CronometroRepository;
import com.control.ata.repository.torneio.PlacarRepository;
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
public class PlacarController {

    @Autowired
    private PlanilhaChaveamentoIndividualRepository planilhaChaveamentoIndividualRepository;
    @Autowired
    private PlanilhaListaIndividualRepository planilhaListaIndividualRepository;
    @Autowired
    private PlacarRepository placarRepository;
    @Autowired
    private RingueIndividualRepository ringueIndividualRepository;
    @Autowired
    private CronometroRepository cronometroRepository;
    @Autowired
    private ChaveLutaIndividualRepository chaveLutaIndividualRepository;

    // ======================================PLACAR=============================================

    @GetMapping("/placar")
    public String placar() {
        return "placar";
    }

    // ======================================MODEL ATTRIBUTES=============================================

    @PostMapping("/ringue/individual/placar/save/{id}")
    public ResponseEntity<?> setPlacar(@Valid @RequestBody String json, @PathVariable("id") Integer id,
                                       BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        PlacarController.PlacarDTO placarDTO = jacksonObjectMapper.readValue(decodedJson, PlacarController.PlacarDTO.class);

        Placar placar = ringueIndividualRepository.getOne(id).getPlacar();

        placar.setId_plan(placarDTO.id_plan);
        placar.setTipo_plan(placarDTO.tipo_plan);
        placar.setId_chave(placarDTO.id_chave);

        placarRepository.save(placar);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/ringue/individual/cronometro/get/{id}")
    public ResponseEntity<?> getCronometro(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(cronometroRepository.getByRingueIndividual(ringueIndividualRepository.getOne(id)));
    }

    @PostMapping("/ringue/individual/get/{id}")
    public ResponseEntity<RingueIndividual> getRingueIndividual(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ringueIndividualRepository.getOne(id));
    }

    @PostMapping("/planilha/individual/chave/luta/{id}")
    public ResponseEntity<?> getChave_luta(@PathVariable("id") Integer id){
        return ResponseEntity.ok(chaveLutaIndividualRepository.getOne(id));
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

    private static class PlacarDTO {
        public Integer id_plan;
        public String tipo_plan;
        public Integer id_chave;

        public PlacarDTO(Integer id_plan, String tipo_plan) {
            this.id_plan = id_plan;
            this.tipo_plan = tipo_plan;
        }

        public PlacarDTO(Integer id_plan, String tipo_plan, Integer id_chave) {
            this.id_plan = id_plan;
            this.tipo_plan = tipo_plan;
            this.id_chave = id_chave;
        }

        public PlacarDTO() {
        }
    }

}

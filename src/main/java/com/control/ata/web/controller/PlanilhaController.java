package com.control.ata.web.controller;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.repository.individual.PlanilhaChaveamentoIndividualRepository;
import com.control.ata.repository.individual.PlanilhaListaIndividualRepository;
import com.control.ata.repository.individual.RingueIndividualRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    // ======================================PLANILHA=============================================

    @GetMapping("/planilha")
    public String planilha() {
        return "planilha";
    }

    // ======================================MODEL ATTRIBUTES=============================================

    @PostMapping("/torneio/numero/ringues/{id}")
    public ResponseEntity<?> getNumeroRingues(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(torneioRepository.getOne(id).getMaxNumeroRingues());
    }

    @PostMapping("/ringue/lista/{numero}")
    public ResponseEntity<?> getNumeroListaRingues(@PathVariable("numero") Integer numero) {
        return ResponseEntity.ok(ringueIndividualRepository.getAllByNumeroRingueAndFinalizado(numero, false));
    }

    @PostMapping("/ringue/lista/planilhas/lista/{id}")
    public ResponseEntity<?> getRinguePlanilhasLista(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(planilhaListaIndividualRepository.getAllByRingueIndividual(ringueIndividualRepository.getOne(id)));
    }

    @PostMapping("/ringue/lista/planilhas/chave/{id}")
    public ResponseEntity<?> getRinguePlanilhasChaves(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(planilhaChaveamentoIndividualRepository.getAllByRingueIndividual(ringueIndividualRepository.getOne(id)));
    }

    @PostMapping("/ringue/finalizar/{id}")
    public ResponseEntity<?> finalizaRingue(@PathVariable("id")Integer id){
        RingueIndividual ringueIndividual = ringueIndividualRepository.getOne(id);
        ringueIndividual.setFinalizado(true);
        ringueIndividualRepository.save(ringueIndividual);
        return ResponseEntity.ok().build();
    }

}

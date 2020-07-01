package com.control.ata.service.planilhaIndividual;

import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.individual.PlanilhaListaIndividualRepository;
import com.control.ata.repository.individual.RingueIndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Lista {

    @Autowired
    private RingueIndividualRepository ringueIndividualRepository;
    @Autowired
    private PlanilhaListaIndividualRepository planilhaListaIndividualRepository;

    public void createPlanilha(RingueIndividual ringueIndividual, CategoriaCompeticao categoriaCompeticao){
        ArrayList<Competidor> competidorArrayList = (ArrayList<Competidor>) ringueIndividualRepository.getCompetidoresByRingueIndividual(ringueIndividual);
        for (Competidor competidor: competidorArrayList){
            planilhaListaIndividualRepository.save(new PlanilhaListaIndividual(competidor, categoriaCompeticao, ringueIndividual));
        }
    }

    public void setPlanilha(PlanilhaListaIndividual planilha){
        planilhaListaIndividualRepository.updatePlanilha(planilha);
    }

}

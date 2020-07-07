package com.control.ata.service.planilhaIndividual;

import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.individual.PlanilhaListaIndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListaIndividual {

    @Autowired
    private PlanilhaListaIndividualRepository planilhaListaIndividualRepository;

    public List<PlanilhaListaIndividual> createPlanilha(RingueIndividual ringueIndividual,
            CategoriaCompeticao categoriaCompeticao) {
        List<PlanilhaListaIndividual> list = new ArrayList<>();
        ArrayList<Competidor> competidorArrayList = new ArrayList<>(ringueIndividual.getCompetidor());
        for (Competidor competidor : competidorArrayList) {
            list.add(planilhaListaIndividualRepository.save(
                    new PlanilhaListaIndividual(competidor, categoriaCompeticao, ringueIndividual)));
        }
        return list;
    }

    public void setPlanilha(PlanilhaListaIndividual planilha) {
        planilhaListaIndividualRepository.save(planilha);
    }

}

package com.control.ata.service.planilhaIndividual;

import com.control.ata.Singleton;
import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.individual.PlanilhaListaIndividualRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ListaIndividual {

    Singleton s = Singleton.getSingleton();

    @Autowired
    private PlanilhaListaIndividualRepository planilhaListaIndividualRepository;
    @Autowired
    private TituloRepository tituloRepository;

    public List<PlanilhaListaIndividual> createPlanilha(RingueIndividual ringueIndividual,
            CategoriaCompeticao categoriaCompeticao) {

        List<PlanilhaListaIndividual> list = new ArrayList<>();

        ArrayList<Competidor> competidorArrayList = new ArrayList<>(ringueIndividual.getCompetidor());
        Competidor competidorTitulo = null;

        if (!ringueIndividual.getFechado()) {
            competidorTitulo = this.sort(competidorArrayList, categoriaCompeticao);
            competidorArrayList.remove(competidorTitulo);
        }

        while (competidorArrayList.size() > 0) {
            int indi = s.getRandomInt(0, competidorArrayList.size());
            list.add(planilhaListaIndividualRepository.save(
                    new PlanilhaListaIndividual(competidorArrayList.get(indi), categoriaCompeticao, ringueIndividual)));
            competidorArrayList.remove(indi);
        }

        if (competidorTitulo != null) {
            list.add(planilhaListaIndividualRepository.save(
                    new PlanilhaListaIndividual(competidorTitulo, categoriaCompeticao, ringueIndividual)));
        }

        return list;
    }

    public PlanilhaListaIndividual setPlanilha(PlanilhaListaIndividual planilha) {
        return planilhaListaIndividualRepository.save(planilha);
    }

    private Competidor sort(Collection<Competidor> competidorList,
            CategoriaCompeticao categoriaCompeticao) {//retorna o maior titulo
        return Singleton.getCompetidorTitulo(competidorList, categoriaCompeticao, tituloRepository);
    }

}

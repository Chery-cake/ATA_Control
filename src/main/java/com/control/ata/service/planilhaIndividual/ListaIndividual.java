package com.control.ata.service.planilhaIndividual;

import com.control.ata.Singleton;
import com.control.ata.model.individual.ChaveListaIndividual;
import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.individual.ChaveListaIndividualRepository;
import com.control.ata.repository.individual.PlanilhaListaIndividualRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ListaIndividual {

    Singleton s = Singleton.getSingleton();

    @Autowired
    private PlanilhaListaIndividualRepository planilhaListaIndividualRepository;
    @Autowired
    private TituloRepository tituloRepository;
    @Autowired
    private ChaveListaIndividualRepository chaveListaIndividualRepository;

    public PlanilhaListaIndividual createPlanilha(RingueIndividual ringueIndividual,//todo verificar a categoria dos competidores e da planilha
            CategoriaCompeticao categoriaCompeticao) {
        ArrayList<Competidor> competidorArrayList = new ArrayList<>(ringueIndividual.getCompetidor());
        Competidor competidorTitulo = null;
        PlanilhaListaIndividual planilhaListaIndividual = new PlanilhaListaIndividual(categoriaCompeticao,
                                                                                      ringueIndividual);
        planilhaListaIndividual = planilhaListaIndividualRepository.save(planilhaListaIndividual);

        if (!ringueIndividual.getFechado()) {
            competidorTitulo = this.sort(competidorArrayList, categoriaCompeticao);
            competidorArrayList.remove(competidorTitulo);
        }

        ArrayList<ChaveListaIndividual> list = new ArrayList<>();

        while (competidorArrayList.size() > 0) {
            int indi = s.getRandomInt(0, competidorArrayList.size());
            list.add(chaveListaIndividualRepository.save(
                    new ChaveListaIndividual(competidorArrayList.get(indi), planilhaListaIndividual)));
            competidorArrayList.remove(indi);
        }

        if (competidorTitulo != null) {
            list.add(chaveListaIndividualRepository.save(
                    new ChaveListaIndividual(competidorTitulo, planilhaListaIndividual)));
        }

        planilhaListaIndividual.setChaveListaIndividualList(list);

        return planilhaListaIndividualRepository.save(planilhaListaIndividual);
    }

    public ChaveListaIndividual setChavePlanilha(ChaveListaIndividual chaveListaIndividual) {
        return chaveListaIndividualRepository.save(chaveListaIndividual);
    }

    private Competidor sort(Collection<Competidor> competidorList,
            CategoriaCompeticao categoriaCompeticao) {//retorna o maior titulo
        return Singleton.getCompetidorTitulo(competidorList, categoriaCompeticao, tituloRepository);
    }

}

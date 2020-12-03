package com.control.ata.service.planilhaIndividual;

import com.control.ata.Singleton;
import com.control.ata.model.individual.ChaveListaIndividual;
import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.individual.ChaveListaIndividualRepository;
import com.control.ata.repository.individual.ListaCategoriaCompetidorFechadaRepository;
import com.control.ata.repository.individual.PlanilhaListaIndividualRepository;
import com.control.ata.repository.tipo_pessoa.CompetidorRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
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
    @Autowired
    private ChaveListaIndividualRepository chaveListaIndividualRepository;
    @Autowired
    private CategoriaCompeticaoRepository categoriaCompeticaoRepository;
    @Autowired
    private ListaCategoriaCompetidorFechadaRepository listaCategoriaCompetidorFechadaRepository;
    @Autowired
    private CompetidorRepository competidorRepository;

    public Collection<PlanilhaListaIndividual> createPlanilhasLista(
            RingueIndividual ringueIndividual) {

        List<PlanilhaListaIndividual> planilhaListaIndividualList = new ArrayList<>();

        ArrayList<Competidor> competidorArrayList = new ArrayList<>(
                competidorRepository.getAllByRingueIndividual(ringueIndividual.getId()));
        Competidor competidorTitulo = null;

        for (CategoriaCompeticao categoriaCompeticao : categoriaCompeticaoRepository.getAllByRingueIndividual(
                ringueIndividual.getId())) {

            if (!categoriaCompeticao.getTipoChave()) {

                ArrayList<Competidor> competidorPlanilha = new ArrayList<>();

                if (ringueIndividual.getFechado()) {
                    for (Competidor competidor : competidorArrayList) {
                        for (CategoriaCompeticao compCat : categoriaCompeticaoRepository.getAllByListaCategoriaCompetidorFechadaList(
                                listaCategoriaCompetidorFechadaRepository.getByCompetidor(competidor).getId())) {
                            if (compCat.getId().equals(categoriaCompeticao.getId())) {
                                competidorPlanilha.add(competidor);
                            }
                        }
                    }
                } else {
                    for (Competidor competidor : competidorArrayList) {
                        for (CategoriaCompeticao compCat : categoriaCompeticaoRepository.getAllByCompetidor(
                                competidor.getId())) {
                            if (compCat.getId().equals(categoriaCompeticao.getId())) {
                                competidorPlanilha.add(competidor);
                            }
                        }
                    }
                }

                PlanilhaListaIndividual planilhaListaIndividual = new PlanilhaListaIndividual(categoriaCompeticao,
                                                                                              ringueIndividual);
                planilhaListaIndividual = planilhaListaIndividualRepository.save(planilhaListaIndividual);

                if (!ringueIndividual.getFechado()) {
                    competidorTitulo = this.sort(competidorPlanilha, categoriaCompeticao);
                    competidorPlanilha.remove(competidorTitulo);
                }

                ArrayList<ChaveListaIndividual> list = new ArrayList<>();

                while (competidorPlanilha.size() > 0) {
                    int indi = s.getRandomInt(0, competidorPlanilha.size());
                    list.add(chaveListaIndividualRepository.save(
                            new ChaveListaIndividual(competidorPlanilha.get(indi), planilhaListaIndividual)));
                    competidorPlanilha.remove(indi);
                }

                if (competidorTitulo != null) {
                    list.add(chaveListaIndividualRepository.save(
                            new ChaveListaIndividual(competidorTitulo, planilhaListaIndividual)));
                }

                planilhaListaIndividual.setChaveListaIndividualList(list);

                planilhaListaIndividualList.add(planilhaListaIndividual);

            }

        }

        return planilhaListaIndividualRepository.saveAll(planilhaListaIndividualList);
    }

    public ChaveListaIndividual setChavePlanilha(ChaveListaIndividual chaveListaIndividual) {
        return chaveListaIndividualRepository.save(chaveListaIndividual);
    }

    private Competidor sort(Collection<Competidor> competidorList,
            CategoriaCompeticao categoriaCompeticao) {//retorna o maior titulo
        return Singleton.getCompetidorTitulo(competidorList, categoriaCompeticao, tituloRepository);
    }

}

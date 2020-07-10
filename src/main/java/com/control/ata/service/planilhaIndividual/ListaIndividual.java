package com.control.ata.service.planilhaIndividual;

import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Titulo;
import com.control.ata.repository.individual.PlanilhaListaIndividualRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ListaIndividual {

    @Autowired
    private PlanilhaListaIndividualRepository planilhaListaIndividualRepository;
    @Autowired
    private TituloRepository tituloRepository;

    public List<PlanilhaListaIndividual> createPlanilha(RingueIndividual ringueIndividual,
            CategoriaCompeticao categoriaCompeticao) {

        List<PlanilhaListaIndividual> list = new ArrayList<>();

        ArrayList<Competidor> competidorArrayList = new ArrayList<>(ringueIndividual.getCompetidor());
        ArrayList<Competidor> competidorTituloArrayList = new ArrayList<>();
        Competidor competidorTitulo = null;

        if (ringueIndividual.getFechado()) {
            for (Competidor value : competidorArrayList) {
                if (!tituloRepository.getAllByCompetidor(value).isEmpty()) {
                    competidorTituloArrayList.add(value);
                }
            }
            for (Competidor competidor : competidorTituloArrayList) {
                competidorArrayList.remove(competidor);
            }

            competidorTituloArrayList = (ArrayList<Competidor>) this.sort(competidorTituloArrayList,
                                                                          categoriaCompeticao);
            competidorArrayList.addAll(competidorTituloArrayList);

            competidorTitulo = competidorArrayList.get(competidorArrayList.size() - 1);
            competidorArrayList.remove(competidorTitulo);
        }

        while (competidorArrayList.size() > 0) {
            int indi = ThreadLocalRandom.current().nextInt(0, competidorArrayList.size());
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

    public void setPlanilha(PlanilhaListaIndividual planilha) {
        planilhaListaIndividualRepository.save(planilha);
    }

    private Collection<Competidor> sort(Collection<Competidor> competidorList,
            CategoriaCompeticao categoriaCompeticao) {//coloca o maior titulo no final
        Competidor[] competidorArray = competidorList.toArray(new Competidor[0]);
        Competidor[] competidorArrayAux = new Competidor[2];

        for (int i = 0; i < competidorArray.length; i++) {
            for (int j = 0; j < competidorArray.length; j++) {
                Titulo titulo1 = tituloRepository.getAllByCompetidorAndCategoriaCompeticao(competidorArray[j],
                                                                                           categoriaCompeticao);
                Titulo titulo2 = null;
                if (j + 1 != competidorArray.length) {
                    titulo2 = tituloRepository.getAllByCompetidorAndCategoriaCompeticao(competidorArray[j + 1],
                                                                                        categoriaCompeticao);
                }

                if (titulo2 != null) {
                    if ((titulo1.getCategoriaTitulo().getPrioridade() > titulo2.getCategoriaTitulo().getPrioridade()) && (titulo1.getAno() > titulo2.getAno())) {
                        competidorArrayAux[0] = competidorArray[j];
                        competidorArrayAux[1] = competidorArray[j + 1];
                        competidorArray[j] = competidorArrayAux[1];
                        competidorArray[j + 1] = competidorArrayAux[0];
                    } else if (titulo1.getAno() > titulo2.getAno()) {
                        competidorArrayAux[0] = competidorArray[j];
                        competidorArrayAux[1] = competidorArray[j + 1];
                        competidorArray[j] = competidorArrayAux[1];
                        competidorArray[j + 1] = competidorArrayAux[0];
                    } else if (titulo1.getCategoriaTitulo().getPrioridade() > titulo2.getCategoriaTitulo().getPrioridade()) {
                        competidorArrayAux[0] = competidorArray[j];
                        competidorArrayAux[1] = competidorArray[j + 1];
                        competidorArray[j] = competidorArrayAux[1];
                        competidorArray[j + 1] = competidorArrayAux[0];
                    }
                }
            }
        }

        return new ArrayList<>(Arrays.asList(competidorArray));
    }
}

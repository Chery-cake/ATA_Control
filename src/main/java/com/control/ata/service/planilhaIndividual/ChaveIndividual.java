package com.control.ata.service.planilhaIndividual;

import com.control.ata.model.individual.ChaveLutaIndividual;
import com.control.ata.model.individual.PlanilhaChaveamentoIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.individual.ChaveLutaIndividualRepository;
import com.control.ata.repository.individual.PlanilhaChaveamentoIndividualRepository;
import com.control.ata.repository.individual.RingueIndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ChaveIndividual {

    @Autowired
    private RingueIndividualRepository ringueIndividualRepository;
    @Autowired
    private PlanilhaChaveamentoIndividualRepository planilhaChaveamentoIndividualRepository;
    @Autowired
    private ChaveLutaIndividualRepository chaveLutaIndividualRepository;

    public PlanilhaChaveamentoIndividual createPlanilha(RingueIndividual ringueIndividual,
            CategoriaCompeticao categoriaCompeticao) {
        ArrayList<Competidor> competidorArrayList = new ArrayList<>(ringueIndividual.getCompetidor());
        PlanilhaChaveamentoIndividual planilha = new PlanilhaChaveamentoIndividual(categoriaCompeticao,
                                                                                   ringueIndividual);
        planilha = planilhaChaveamentoIndividualRepository.save(planilha);
        createChave(competidorArrayList, planilha);
        return planilha;
    }

    public ChaveLutaIndividual updateChave(ChaveLutaIndividual chaveLutaIndividual) {
        return chaveLutaIndividualRepository.save(chaveLutaIndividual);
    }

    private void setChave(Competidor compVer, Competidor compBra, PlanilhaChaveamentoIndividual planilha, int posicao,
            int fase) {
        ChaveLutaIndividual chave = new ChaveLutaIndividual(posicao, fase, compVer, compBra, planilha);
        chaveLutaIndividualRepository.save(chave);
    }

    private void createChave(ArrayList<Competidor> competidorArrayList, PlanilhaChaveamentoIndividual planilha) {
        if (competidorArrayList.size() > 2) {
            int fase = 1;
            if ((competidorArrayList.size() > 4) && (competidorArrayList.size() <= 8)) {
                fase = 2;
            } else if ((competidorArrayList.size() > 8) && (competidorArrayList.size() <= 16)) {
                fase = 3;
            } else if ((competidorArrayList.size() > 16) && (competidorArrayList.size() <= 32)) {
                fase = 4;
            }
            int i = 1;
            while (competidorArrayList.size() > 0) {
                Sorteio sorteio = new Sorteio();
                sorteio = sorteio.sorteio(competidorArrayList);
                setChave(sorteio.a, sorteio.b, planilha, i, fase);
                i++;
            }
        } else if (competidorArrayList.size() == 2) {
            setChave(competidorArrayList.get(0), competidorArrayList.get(1), planilha, 1, 0);
        } else if (competidorArrayList.size() == 1) {
            setChave(competidorArrayList.get(0), null, planilha, 1, 0);
        }
    }

    private class Sorteio {
        Competidor a;
        Competidor b;

        Sorteio sorteio(ArrayList<Competidor> competidorArrayList) {
            Sorteio sorteio = new Sorteio();
            if (competidorArrayList.size() > 1) {
                int size = competidorArrayList.size();
                int indiA = ThreadLocalRandom.current().nextInt(0, size);
                int indiB = indiA;

                while (indiB == indiA) {
                    indiB = ThreadLocalRandom.current().nextInt(0, size);
                }
                sorteio.a = competidorArrayList.get(indiA);
                sorteio.b = competidorArrayList.get(indiB);
                if (indiA > indiB) {
                    competidorArrayList.remove(indiA);
                    competidorArrayList.remove(indiB);
                } else {
                    competidorArrayList.remove(indiB);
                    competidorArrayList.remove(indiA);
                }
            } else {
                int size = competidorArrayList.size();
                int indiA = ThreadLocalRandom.current().nextInt(0, size);
                sorteio.a = competidorArrayList.get(indiA);
                sorteio.b = null;
                competidorArrayList.remove(indiA);
            }
            return sorteio;
        }
    }

}

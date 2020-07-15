package com.control.ata.service.planilhaIndividual;

import com.control.ata.Singleton;
import com.control.ata.model.individual.ChaveLutaIndividual;
import com.control.ata.model.individual.PlanilhaChaveamentoIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.individual.ChaveLutaIndividualRepository;
import com.control.ata.repository.individual.PlanilhaChaveamentoIndividualRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ChaveIndividual {

    private final Singleton s = Singleton.getSingleton();

    @Autowired
    private PlanilhaChaveamentoIndividualRepository planilhaChaveamentoIndividualRepository;
    @Autowired
    private ChaveLutaIndividualRepository chaveLutaIndividualRepository;
    @Autowired
    private TituloRepository tituloRepository;

    public PlanilhaChaveamentoIndividual createPlanilha(RingueIndividual ringueIndividual,
            CategoriaCompeticao categoriaCompeticao) {
        ArrayList<Competidor> competidorArrayList = new ArrayList<>(ringueIndividual.getCompetidor());
        PlanilhaChaveamentoIndividual planilha = new PlanilhaChaveamentoIndividual(categoriaCompeticao,
                                                                                   ringueIndividual);
        planilha = planilhaChaveamentoIndividualRepository.save(planilha);
        createChave(competidorArrayList, planilha);
        return planilha;
    }

    public ChaveLutaIndividual updateChave(
            ChaveLutaIndividual chaveLutaIndividual) {//todo fazer com que chaves com 1 competidor passem pra proxima fase
        chaveLutaIndividual = chaveLutaIndividualRepository.save(chaveLutaIndividual);
        if (chaveLutaIndividual.getDesqualificacaoBranca()) {
            nextChave(chaveLutaIndividual.getCompetidorVermelho(), chaveLutaIndividual);
        } else if (chaveLutaIndividual.getDesqualificacaoVermelha()) {
            nextChave(chaveLutaIndividual.getCompetidorBranco(), chaveLutaIndividual);
        } else if (chaveLutaIndividual.getPontosBrancos() >= chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao().getLimitePonto()) {
            nextChave(chaveLutaIndividual.getCompetidorBranco(), chaveLutaIndividual);
        } else if (chaveLutaIndividual.getPontosVermelhos() >= chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao().getLimitePonto()) {
            nextChave(chaveLutaIndividual.getCompetidorVermelho(), chaveLutaIndividual);
        }

        return chaveLutaIndividual;
    }

    private void nextChave(Competidor competidor, ChaveLutaIndividual chaveLutaIndividual) {
        if (chaveLutaIndividual.getFase() - 1 == 0) {
            if (!chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividualAndFase(
                    chaveLutaIndividual.getPlanilhaChaveamentoIndividual(),
                    chaveLutaIndividual.getFase() - 1).isEmpty()) {
                ArrayList<ChaveLutaIndividual> chaveLutaIndividualArrayList = (ArrayList<ChaveLutaIndividual>) chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividualAndFase(
                        chaveLutaIndividual.getPlanilhaChaveamentoIndividual(), chaveLutaIndividual.getFase() - 1);
                ChaveLutaIndividual chaveLutaIndividual2 = null;
                ChaveLutaIndividual chaveLutaIndividual3 = null;
                for (ChaveLutaIndividual chaveLutaIndividual1 : chaveLutaIndividualArrayList) {
                    if ((chaveLutaIndividual1.getCompetidorBranco() == null) && (chaveLutaIndividual1.getPosicao() == 1)) {
                        chaveLutaIndividual2 = chaveLutaIndividual1;
                    }
                    if ((chaveLutaIndividual1.getCompetidorBranco() == null) && (chaveLutaIndividual1.getPosicao() == 2)) {
                        chaveLutaIndividual3 = chaveLutaIndividual1;
                    }
                }
                chaveLutaIndividual2.setCompetidorBranco(competidor);
                chaveLutaIndividualRepository.save(chaveLutaIndividual2);
                Competidor competidor1;
                if (competidor == chaveLutaIndividual.getCompetidorBranco()) {
                    competidor1 = chaveLutaIndividual.getCompetidorVermelho();
                } else {
                    competidor1 = chaveLutaIndividual.getCompetidorBranco();
                }
                chaveLutaIndividual3.setCompetidorBranco(competidor1);
                chaveLutaIndividualRepository.save(chaveLutaIndividual3);
            } else {
                this.setChave(competidor, null, chaveLutaIndividual.getPlanilhaChaveamentoIndividual(), 1,
                              chaveLutaIndividual.getFase() - 1);
                Competidor competidor1;
                if (competidor == chaveLutaIndividual.getCompetidorBranco()) {
                    competidor1 = chaveLutaIndividual.getCompetidorVermelho();
                } else {
                    competidor1 = chaveLutaIndividual.getCompetidorBranco();
                }
                this.setChave(competidor1, null, chaveLutaIndividual.getPlanilhaChaveamentoIndividual(), 2,
                              chaveLutaIndividual.getFase() - 1);
            }
        } else if (!chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividualAndFase(
                chaveLutaIndividual.getPlanilhaChaveamentoIndividual(), chaveLutaIndividual.getFase() - 1).isEmpty()) {
            ArrayList<ChaveLutaIndividual> chaveLutaIndividualArrayList = (ArrayList<ChaveLutaIndividual>) chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividualAndFase(
                    chaveLutaIndividual.getPlanilhaChaveamentoIndividual(), chaveLutaIndividual.getFase() - 1);
            ChaveLutaIndividual chaveLutaIndividual2 = null;
            boolean semChave = true;
            for (ChaveLutaIndividual chaveLutaIndividual1 : chaveLutaIndividualArrayList) {
                if (chaveLutaIndividual1.getCompetidorBranco() == null) {
                    chaveLutaIndividual2 = chaveLutaIndividual1;
                    semChave = false;
                    break;
                }
            }
            if (semChave) {
                this.setChave(competidor, null, chaveLutaIndividual.getPlanilhaChaveamentoIndividual(),
                              chaveLutaIndividualArrayList.size() + 1,
                              chaveLutaIndividual.getFase() - 1);
            } else {
                chaveLutaIndividual2.setCompetidorBranco(competidor);
                chaveLutaIndividualRepository.save(chaveLutaIndividual2);
            }
        } else {
            if (chaveLutaIndividual.getFase() != 0) {
                this.setChave(competidor, null, chaveLutaIndividual.getPlanilhaChaveamentoIndividual(), 1,
                              chaveLutaIndividual.getFase() - 1);
            }
        }
    }

    private ChaveLutaIndividual setChave(Competidor compVer, Competidor compBra, PlanilhaChaveamentoIndividual planilha,
            int posicao,
            int fase) {
        return chaveLutaIndividualRepository.save(new ChaveLutaIndividual(posicao, fase, compVer, compBra, planilha));
    }

    private void createChave(ArrayList<Competidor> competidorArrayList, PlanilhaChaveamentoIndividual planilha) {//todo testar sobre ser da mesma academia
        if (planilha.getRingueIndividual().getFechado()) {
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
                    Singleton.Sorteio sorteio = new Singleton.Sorteio();
                    sorteio = sorteio.sorteio(competidorArrayList);
                    setChave((Competidor) sorteio.a, (Competidor) sorteio.b, planilha, i, fase);
                    i++;
                }
            } else if (competidorArrayList.size() == 2) {
                setChave(competidorArrayList.get(0), competidorArrayList.get(1), planilha, 1, 0);
            } else if (competidorArrayList.size() == 1) {
                setChave(competidorArrayList.get(0), null, planilha, 1, 0);
            }
        } else {
            Competidor competidorTitulo = this.sort(competidorArrayList, planilha.getCategoriaCompeticao());
            competidorArrayList.remove(competidorTitulo);

            boolean competidorTituloAdicionado = false;

            if (competidorArrayList.size() + 1 > 2) {
                int fase = 1;
                if ((competidorArrayList.size() + 1 > 4) && (competidorArrayList.size() <= 8)) {
                    fase = 2;
                } else if ((competidorArrayList.size() + 1 > 8) && (competidorArrayList.size() <= 16)) {
                    fase = 3;
                } else if ((competidorArrayList.size() + 1 > 16) && (competidorArrayList.size() <= 32)) {
                    fase = 4;
                }
                int i = 1;
                while (competidorArrayList.size() > 0) {
                    if (competidorArrayList.size() == 1) {
                        setChave(competidorArrayList.get(0), competidorTitulo, planilha, i, fase);
                        competidorArrayList.remove(0);
                        competidorTituloAdicionado = true;
                    } else {
                        Singleton.Sorteio sorteio = new Singleton.Sorteio();
                        sorteio = sorteio.sorteio(competidorArrayList);
                        setChave((Competidor) sorteio.a, (Competidor) sorteio.b, planilha, i, fase);
                    }
                    i++;
                }
                if (!competidorTituloAdicionado) {
                    setChave(competidorTitulo, null, planilha, i, fase);
                }
            } else if (competidorArrayList.size() + 1 == 2) {
                setChave(competidorArrayList.get(0), competidorTitulo, planilha, 1, 0);
            } else if (competidorArrayList.size() == 0) {
                setChave(competidorTitulo, null, planilha, 1, 0);
            }

        }

    }

    private Competidor sort(Collection<Competidor> competidorList,
            CategoriaCompeticao categoriaCompeticao) {//retorna o maior titulo
        return Singleton.getCompetidorTitulo(competidorList, categoriaCompeticao, tituloRepository);
    }

}

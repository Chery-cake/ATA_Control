package com.control.ata.service.planilhaIndividual;

import com.control.ata.Singleton;
import com.control.ata.model.individual.*;
import com.control.ata.model.torneio.CategoriaTorneio;
import com.control.ata.repository.individual.ChaveListaIndividualRepository;
import com.control.ata.repository.individual.ChaveLutaIndividualRepository;
import com.control.ata.repository.individual.RankingIndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class RankIndividual {

    private final Singleton s = Singleton.getSingleton();
    @Autowired
    private RankingIndividualRepository rankingIndividualRepository;
    @Autowired
    private ChaveListaIndividualRepository chaveListaIndividualRepository;
    @Autowired
    private ChaveLutaIndividualRepository chaveLutaIndividualRepository;
    private Integer valorBase = s.valorBase;

    private List<ChaveListaIndividual> sort(Collection<ChaveListaIndividual> chaveListaIndividualCollection) {
        ChaveListaIndividual[] chaveArray = chaveListaIndividualCollection.toArray(new ChaveListaIndividual[0]);
        ChaveListaIndividual[] chaveArrayAux = new ChaveListaIndividual[2];

        for (int i = 0; i < chaveArray.length; i++) {
            for (int j = 0; j < chaveArray.length; j++) {
                ChaveListaIndividual chave1 = chaveArray[j];
                ChaveListaIndividual chave2 = null;
                if (j + 1 != chaveArray.length) {
                    chave2 = chaveArray[j + 1];
                }
                if ((chave1 != null) && (chave2 != null)) {
                    if (chave1.getSoma() > chave2.getSoma()) {
                        chaveArrayAux[0] = chaveArray[j];
                        chaveArrayAux[1] = chaveArray[j + 1];
                        chaveArray[j] = chaveArrayAux[1];
                        chaveArray[j + 1] = chaveArrayAux[0];
                    }
                } else if ((chave1 != null) && (j + 1 != chaveArray.length)) {
                    chaveArrayAux[0] = chaveArray[j];
                    chaveArrayAux[1] = chaveArray[j + 1];
                    chaveArray[j] = chaveArrayAux[1];
                    chaveArray[j + 1] = chaveArrayAux[0];
                }
            }
        }
        return this.reverse(Arrays.asList(chaveArray));
    }

    private List<ChaveListaIndividual> reverse(Collection<ChaveListaIndividual> collection) {
        ChaveListaIndividual[] chaveArray = collection.toArray(new ChaveListaIndividual[0]);
        int size = chaveArray.length;
        ChaveListaIndividual[] chaveArrayAux = new ChaveListaIndividual[size];
        for (int i = 0; i < chaveArray.length; i++) {
            size--;
            chaveArrayAux[i] = chaveArray[size];
        }
        return new ArrayList<>(Arrays.asList(chaveArrayAux));
    }

    public List<RankingIndividual> setRankingLista(PlanilhaListaIndividual planilhaListaIndividual) {
        List<RankingIndividual> list = new ArrayList<>();

        CategoriaTorneio categoriaTorneio = planilhaListaIndividual.getRingueIndividual().getTorneio().getCategoriaTorneio();
        valorBase = valorBase * categoriaTorneio.getPrioridade();

        ArrayList<ChaveListaIndividual> chaveListaIndividualArrayList = new ArrayList<>(this.sort(
                chaveListaIndividualRepository.getAllByPlanilhaChaveamentoIndividual(planilhaListaIndividual)));


        for (ChaveListaIndividual chaveListaIndividual : chaveListaIndividualArrayList) {
            if (rankingIndividualRepository.getByPessoaAndCategoriaCompeticao(
                    chaveListaIndividual.getCompetidor().getPessoa(),
                    planilhaListaIndividual.getCategoriaCompeticao()) != null) {
                RankingIndividual rankingIndividual = rankingIndividualRepository.getByPessoaAndCategoriaCompeticao(
                        chaveListaIndividual.getCompetidor().getPessoa(),
                        planilhaListaIndividual.getCategoriaCompeticao());
                rankingIndividual.setPontuacao(rankingIndividual.getPontuacao() + valorBase);
                list.add(rankingIndividualRepository.save(rankingIndividual));
            } else {
                list.add(rankingIndividualRepository.save(
                        new RankingIndividual(chaveListaIndividual.getCompetidor().getPessoa(), valorBase,
                                              planilhaListaIndividual.getCategoriaCompeticao())));
            }
            valorBase = s.getValorBase(valorBase);
        }
        return list;
    }

    public List<RankingIndividual> setRankingChave(PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual) {
        List<RankingIndividual> list = new ArrayList<>();

        CategoriaTorneio categoriaTorneio = planilhaChaveamentoIndividual.getRingueIndividual().getTorneio().getCategoriaTorneio();
        valorBase = valorBase * categoriaTorneio.getPrioridade();

        ArrayList<ChaveLutaIndividual> chaveLutaIndividualArrayList = new ArrayList<>(
                chaveLutaIndividualRepository.getAllByPlanilhaChaveamentoIndividualAndFase(
                        planilhaChaveamentoIndividual, 0));

        for (ChaveLutaIndividual chaveLutaIndividual : chaveLutaIndividualArrayList) {
            if (chaveLutaIndividual.getDesqualificacaoBranca()) {
                saveChaveLutaVer(list, chaveLutaIndividual);
                valorBase = s.getValorBase(valorBase);
                saveChaveLutaBra(list, chaveLutaIndividual);
            } else if (chaveLutaIndividual.getDesqualificacaoVermelha()) {
                saveChaveLutaBra(list, chaveLutaIndividual);
                valorBase = s.getValorBase(valorBase);
                saveChaveLutaVer(list, chaveLutaIndividual);
            } else if (chaveLutaIndividual.getPontosVermelhos() >= chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao().getLimitePonto()) {
                saveChaveLutaVer(list, chaveLutaIndividual);
                valorBase = s.getValorBase(valorBase);
                saveChaveLutaBra(list, chaveLutaIndividual);
            } else if (chaveLutaIndividual.getPontosBrancos() >= chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao().getLimitePonto()) {
                saveChaveLutaBra(list, chaveLutaIndividual);
                valorBase = s.getValorBase(valorBase);
                saveChaveLutaVer(list, chaveLutaIndividual);
            }
            valorBase = s.getValorBase(valorBase);
        }
        return list;
    }

    private void saveChaveLutaVer(List<RankingIndividual> list, ChaveLutaIndividual chaveLutaIndividual) {
        if (rankingIndividualRepository.getByPessoaAndCategoriaCompeticao(
                chaveLutaIndividual.getCompetidorVermelho().getPessoa(),
                chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao()) != null) {
            RankingIndividual rankingIndividual = rankingIndividualRepository.getByPessoaAndCategoriaCompeticao(
                    chaveLutaIndividual.getCompetidorVermelho().getPessoa(),
                    chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao());
            rankingIndividual.setPontuacao(rankingIndividual.getPontuacao() + valorBase);
            list.add(rankingIndividualRepository.save(rankingIndividual));
        } else {
            list.add(rankingIndividualRepository.save(
                    new RankingIndividual(chaveLutaIndividual.getCompetidorVermelho().getPessoa(), valorBase,
                                          chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao())));
        }
    }

    private void saveChaveLutaBra(List<RankingIndividual> list, ChaveLutaIndividual chaveLutaIndividual) {
        if (rankingIndividualRepository.getByPessoaAndCategoriaCompeticao(
                chaveLutaIndividual.getCompetidorBranco().getPessoa(),
                chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao()) != null) {
            RankingIndividual rankingIndividual = rankingIndividualRepository.getByPessoaAndCategoriaCompeticao(
                    chaveLutaIndividual.getCompetidorBranco().getPessoa(),
                    chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao());
            rankingIndividual.setPontuacao(rankingIndividual.getPontuacao() + valorBase);
            list.add(rankingIndividualRepository.save(rankingIndividual));
        } else {
            list.add(rankingIndividualRepository.save(
                    new RankingIndividual(chaveLutaIndividual.getCompetidorBranco().getPessoa(), valorBase,
                                          chaveLutaIndividual.getPlanilhaChaveamentoIndividual().getCategoriaCompeticao())));
        }
    }
}

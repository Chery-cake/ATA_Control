package com.control.ata.service.planilhaTime;

import com.control.ata.Singleton;
import com.control.ata.model.time.*;
import com.control.ata.model.torneio.CategoriaTorneio;
import com.control.ata.repository.time.ChaveListaTimeRepository;
import com.control.ata.repository.time.ChaveLutaTimeRepository;
import com.control.ata.repository.time.RankingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class RankTime {

    private final Singleton s = Singleton.getSingleton();
    private Integer valorBase = s.valorBase;
    @Autowired
    private RankingTimeRepository rankingTimeRepository;
    @Autowired
    private ChaveListaTimeRepository chaveListaTimeRepository;
    @Autowired
    private ChaveLutaTimeRepository chaveLutaTimeRepository;

    private List<ChaveListaTime> sort(Collection<ChaveListaTime> chaveListaTimeCollection) {
        ChaveListaTime[] chaveArray = chaveListaTimeCollection.toArray(new ChaveListaTime[0]);
        ChaveListaTime[] chaveArrayAux = new ChaveListaTime[2];

        for (int i = 0; i < chaveArray.length; i++) {
            for (int j = 0; j < chaveArray.length; j++) {
                ChaveListaTime chave1 = chaveArray[j];
                ChaveListaTime chave2 = null;
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

    private List<ChaveListaTime> reverse(Collection<ChaveListaTime> collection) {
        ChaveListaTime[] chaveArray = collection.toArray(new ChaveListaTime[0]);
        int size = chaveArray.length;
        ChaveListaTime[] chaveArrayAux = new ChaveListaTime[size];
        for (int i = 0; i < chaveArray.length; i++) {
            size--;
            chaveArrayAux[i] = chaveArray[size];
        }
        return new ArrayList<>(Arrays.asList(chaveArrayAux));
    }

    public List<RankingTime> setRankingLista(PlanilhaListaTime planilhaListaTime) {
        List<RankingTime> list = new ArrayList<>();

        CategoriaTorneio categoriaTorneio = planilhaListaTime.getRingueTime().getTorneio().getCategoriaTorneio();
        valorBase = valorBase * categoriaTorneio.getPrioridade();

        ArrayList<ChaveListaTime> chaveListaTimeArrayList = new ArrayList<>(
                this.sort(chaveListaTimeRepository.getAllByPlanilhaListaTime(planilhaListaTime)));

        for (ChaveListaTime chaveListaTime : chaveListaTimeArrayList) {
            if (rankingTimeRepository.getByTimeAndCategoriaCompeticao(chaveListaTime.getTime(),
                                                                      planilhaListaTime.getCategoriaCompeticao()) != null) {
                RankingTime rankingTime = rankingTimeRepository.getByTimeAndCategoriaCompeticao(
                        chaveListaTime.getTime(), planilhaListaTime.getCategoriaCompeticao());
                rankingTime.setPontuacao(rankingTime.getPontuacao() + valorBase);
                list.add(rankingTimeRepository.save(rankingTime));
            } else {
                list.add(rankingTimeRepository.save(new RankingTime(chaveListaTime.getTime(), valorBase,
                                                                    planilhaListaTime.getCategoriaCompeticao())));
            }
        }
        valorBase = s.getValorBase(valorBase);
        return list;
    }

    public List<RankingTime> setRankingChave(PlanilhaChaveamentoTime planilhaChaveamentoTime) {
        List<RankingTime> list = new ArrayList<>();

        CategoriaTorneio categoriaTorneio = planilhaChaveamentoTime.getRingueTime().getTorneio().getCategoriaTorneio();
        valorBase = valorBase * categoriaTorneio.getPrioridade();

        ArrayList<ChaveLutaTime> chaveLutaTimeArrayList = new ArrayList<>(
                chaveLutaTimeRepository.getAllByPlanilhaChaveamentoTimeAndFase(planilhaChaveamentoTime, 0));

        for (ChaveLutaTime chaveLutaTime : chaveLutaTimeArrayList) {
            if (chaveLutaTime.getDesqualificacaoVermelha()) {
                saveChaveLutaVer(list, chaveLutaTime);
                valorBase = s.getValorBase(valorBase);
                saveChaveLutaBra(list, chaveLutaTime);
            } else if (chaveLutaTime.getDesqualificacaoBranca()) {
                saveChaveLutaBra(list, chaveLutaTime);
                valorBase = s.getValorBase(valorBase);
                saveChaveLutaVer(list, chaveLutaTime);
            } else if (chaveLutaTime.getPontosTotaisVermelhos() > chaveLutaTime.getPontosTotaisBrancos()) {
                saveChaveLutaVer(list, chaveLutaTime);
                valorBase = s.getValorBase(valorBase);
                saveChaveLutaBra(list, chaveLutaTime);
            } else {
                saveChaveLutaBra(list, chaveLutaTime);
                valorBase = s.getValorBase(valorBase);
                saveChaveLutaVer(list, chaveLutaTime);
            }
            valorBase = s.getValorBase(valorBase);
        }

        return list;
    }

    private void saveChaveLutaVer(List<RankingTime> list, ChaveLutaTime chaveLutaTime) {
        if (rankingTimeRepository.getByTimeAndCategoriaCompeticao(chaveLutaTime.getTimeVermelho(),
                                                                  chaveLutaTime.getPlanilhaChaveamentoTime().getCategoriaCompeticao()) != null) {
            RankingTime rankingTime = rankingTimeRepository.getByTimeAndCategoriaCompeticao(
                    chaveLutaTime.getTimeVermelho(),
                    chaveLutaTime.getPlanilhaChaveamentoTime().getCategoriaCompeticao());
            rankingTime.setPontuacao(rankingTime.getPontuacao() + valorBase);
            list.add(rankingTimeRepository.save(rankingTime));
        } else {
            list.add(rankingTimeRepository.save(new RankingTime(chaveLutaTime.getTimeVermelho(), valorBase,
                                                                chaveLutaTime.getPlanilhaChaveamentoTime().getCategoriaCompeticao())));
        }
    }

    private void saveChaveLutaBra(List<RankingTime> list, ChaveLutaTime chaveLutaTime) {
        if (rankingTimeRepository.getByTimeAndCategoriaCompeticao(chaveLutaTime.getTimeBranco(),
                                                                  chaveLutaTime.getPlanilhaChaveamentoTime().getCategoriaCompeticao()) != null) {
            RankingTime rankingTime = rankingTimeRepository.getByTimeAndCategoriaCompeticao(
                    chaveLutaTime.getTimeBranco(),
                    chaveLutaTime.getPlanilhaChaveamentoTime().getCategoriaCompeticao());
            rankingTime.setPontuacao(rankingTime.getPontuacao() + valorBase);
            list.add(rankingTimeRepository.save(rankingTime));
        } else {
            list.add(rankingTimeRepository.save(new RankingTime(chaveLutaTime.getTimeBranco(), valorBase,
                                                                chaveLutaTime.getPlanilhaChaveamentoTime().getCategoriaCompeticao())));
        }
    }

}

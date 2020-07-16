package com.control.ata.service.planilhaTime;

import com.control.ata.Singleton;
import com.control.ata.model.time.ChaveLutaTime;
import com.control.ata.model.time.PlanilhaChaveamentoTime;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.time.Time;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.time.ChaveLutaTimeRepository;
import com.control.ata.repository.time.PlanilhaChaveamentoTimeRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ChaveTime {

    private final Singleton s = Singleton.getSingleton();

    @Autowired
    private PlanilhaChaveamentoTimeRepository planilhaChaveamentoTimeRepository;
    @Autowired
    private ChaveLutaTimeRepository chaveLutaTimeRepository;
    @Autowired
    private TituloRepository tituloRepository;

    public PlanilhaChaveamentoTime createPlanilha(RingueTime ringueTime, CategoriaCompeticao categoriaCompeticao) {
        ArrayList<Time> timeArrayList = new ArrayList<>(ringueTime.getTime());
        PlanilhaChaveamentoTime planilha = new PlanilhaChaveamentoTime(categoriaCompeticao, ringueTime);
        planilha = planilhaChaveamentoTimeRepository.save(planilha);
        createChave(timeArrayList, planilha);
        return planilha;
    }

    public ChaveLutaTime updateChave(ChaveLutaTime chaveLutaTime) {
        chaveLutaTime = chaveLutaTimeRepository.save(chaveLutaTime);
        if (chaveLutaTime.getDesqualificacaoBranca()) {
            nextChave(chaveLutaTime.getTimeVermelho(), chaveLutaTime);
        } else if (chaveLutaTime.getDesqualificacaoVermelha()) {
            nextChave(chaveLutaTime.getTimeBranco(), chaveLutaTime);
        } else if (chaveLutaTime.getTerminou()) {
            if (chaveLutaTime.getPontosTotaisBrancos() > chaveLutaTime.getPontosTotaisVermelhos()) {
                nextChave(chaveLutaTime.getTimeBranco(), chaveLutaTime);
            } else {
                nextChave(chaveLutaTime.getTimeVermelho(), chaveLutaTime);
            }
        }
        return chaveLutaTime;
    }

    private void nextChave(Time time, ChaveLutaTime chaveLutaTime) {
        if (chaveLutaTime.getFase() - 1 == 0) {
            if (!chaveLutaTimeRepository.getAllByPlanilhaChaveamentoTimeAndFase(
                    chaveLutaTime.getPlanilhaChaveamentoTime(),
                    chaveLutaTime.getFase() - 1).isEmpty()) {
                ArrayList<ChaveLutaTime> chaveLutaTimeArrayList = (ArrayList<ChaveLutaTime>) chaveLutaTimeRepository.getAllByPlanilhaChaveamentoTimeAndFase(
                        chaveLutaTime.getPlanilhaChaveamentoTime(), chaveLutaTime.getFase() - 1);
                ChaveLutaTime chaveLutaTime2 = null;
                ChaveLutaTime chaveLutaTime3 = null;
                for (ChaveLutaTime chaveLutaTime1 : chaveLutaTimeArrayList) {
                    if ((chaveLutaTime1.getTimeBranco() == null) && (chaveLutaTime1.getPosicao() == 1)) {
                        chaveLutaTime2 = chaveLutaTime1;
                    }
                    if ((chaveLutaTime1.getTimeBranco() == null) && (chaveLutaTime1.getPosicao() == 2)) {
                        chaveLutaTime3 = chaveLutaTime1;
                    }
                }
                chaveLutaTime2.setTimeBranco(time);
                chaveLutaTimeRepository.save(chaveLutaTime2);
                Time time1;
                if (time == chaveLutaTime.getTimeBranco()) {
                    time1 = chaveLutaTime.getTimeVermelho();
                } else {
                    time1 = chaveLutaTime.getTimeBranco();
                }
                chaveLutaTime3.setTimeBranco(time1);
                chaveLutaTimeRepository.save(chaveLutaTime3);
            } else {
                this.setChave(time, null, chaveLutaTime.getPlanilhaChaveamentoTime(), 1,
                              chaveLutaTime.getFase() - 1);
                Time time1;
                if (time == chaveLutaTime.getTimeBranco()) {
                    time1 = chaveLutaTime.getTimeVermelho();
                } else {
                    time1 = chaveLutaTime.getTimeBranco();
                }
                this.setChave(time1, null, chaveLutaTime.getPlanilhaChaveamentoTime(), 2,
                              chaveLutaTime.getFase() - 1);
            }
        } else if (!chaveLutaTimeRepository.getAllByPlanilhaChaveamentoTimeAndFase(
                chaveLutaTime.getPlanilhaChaveamentoTime(), chaveLutaTime.getFase() - 1).isEmpty()) {
            ArrayList<ChaveLutaTime> chaveLutaTimeArrayList = (ArrayList<ChaveLutaTime>) chaveLutaTimeRepository.getAllByPlanilhaChaveamentoTimeAndFase(
                    chaveLutaTime.getPlanilhaChaveamentoTime(), chaveLutaTime.getFase() - 1);
            ChaveLutaTime chaveLutaTime2 = null;
            boolean semChave = true;
            for (ChaveLutaTime chaveLutaTime1 : chaveLutaTimeArrayList) {
                if (chaveLutaTime1.getTimeBranco() == null) {
                    chaveLutaTime2 = chaveLutaTime1;
                    semChave = false;
                    break;
                }
            }
            if (semChave) {
                this.setChave(time, null, chaveLutaTime.getPlanilhaChaveamentoTime(),
                              chaveLutaTimeArrayList.size() + 1,
                              chaveLutaTime.getFase() - 1);
            } else {
                chaveLutaTime2.setTimeBranco(time);
                chaveLutaTimeRepository.save(chaveLutaTime2);
            }
        } else {
            if (chaveLutaTime.getFase() != 0) {
                this.setChave(time, null, chaveLutaTime.getPlanilhaChaveamentoTime(), 1,
                              chaveLutaTime.getFase() - 1);
            }
        }
    }

    private ChaveLutaTime setChave(Time timeVer, Time timeBra, PlanilhaChaveamentoTime planilha, int posicao,
            int fase) {
        return chaveLutaTimeRepository.save(new ChaveLutaTime(posicao, fase, timeVer, timeBra, planilha));
    }

    private void createChave(ArrayList<Time> timeArrayList, PlanilhaChaveamentoTime planilha) {
        if (planilha.getRingueTime().getFechado()) {
            if (timeArrayList.size() > 2) {
                int fase = 1;
                if ((timeArrayList.size() > 4) && (timeArrayList.size() <= 8)) {
                    fase = 2;
                } else if ((timeArrayList.size() > 8) && (timeArrayList.size() <= 16)) {
                    fase = 3;
                } else if ((timeArrayList.size() > 16) && (timeArrayList.size() <= 32)) {
                    fase = 4;
                }
                int i = 1;
                while (timeArrayList.size() > 0) {
                    Sorteio sorteio = new Sorteio();
                    sorteio = sorteio.sorteio(timeArrayList);
                    setChave(sorteio.a, sorteio.b, planilha, i, fase);
                    i++;
                }
            } else if (timeArrayList.size() == 2) {
                setChave(timeArrayList.get(0), timeArrayList.get(1), planilha, 1, 0);
            } else if (timeArrayList.size() == 1) {
                setChave(timeArrayList.get(0), null, planilha, 1, 0);
            }
        } else {
            Time timeTitulo = this.sort(timeArrayList, planilha.getCategoriaCompeticao());
            timeArrayList.remove(timeTitulo);
            boolean timeTituloAdicionado = false;
            if (timeArrayList.size() + 1 > 2) {
                int fase = 1;
                if ((timeArrayList.size() > 4) && (timeArrayList.size() <= 8)) {
                    fase = 2;
                } else if ((timeArrayList.size() > 8) && (timeArrayList.size() <= 16)) {
                    fase = 3;
                } else if ((timeArrayList.size() > 16) && (timeArrayList.size() <= 32)) {
                    fase = 4;
                }
                int i = 1;
                while (timeArrayList.size() > 0) {
                    if (timeArrayList.size() == 1) {
                        setChave(timeArrayList.get(0), timeTitulo, planilha, i, fase);
                        timeArrayList.remove(0);
                        timeTituloAdicionado = true;
                    } else {
                        Sorteio sorteio = new Sorteio();
                        sorteio = sorteio.sorteio(timeArrayList);
                        setChave(sorteio.a, sorteio.b, planilha, i, fase);
                    }
                    i++;
                }
                if (!timeTituloAdicionado) {
                    setChave(timeTitulo, null, planilha, i, fase);
                }
            } else if (timeArrayList.size() == 1) {
                setChave(timeArrayList.get(0), timeTitulo, planilha, 1, 0);
            } else if (timeArrayList.size() == 0) {
                setChave(timeTitulo, null, planilha, 1, 0);
            }
        }

    }

    private Time sort(Collection<Time> timeList, CategoriaCompeticao categoriaCompeticao) {
        return Singleton.getTimeTitulo(timeList, categoriaCompeticao, tituloRepository);
    }

    private class Sorteio {
        Time a;
        Time b;
        Singleton s = Singleton.getSingleton();

        public Sorteio sorteio(Iterable<Time> iterable) {
            Sorteio sorteio = new Sorteio();
            ArrayList<Time> arrayList = (ArrayList<Time>) iterable;
            if (arrayList.size() > 1) {
                int indiA = s.getRandomInt(0, arrayList.size());
                int indiB = indiA;

                while (indiB == indiA) {
                    indiB = s.getRandomInt(0, arrayList.size());
                }
                sorteio.a = arrayList.get(indiA);
                sorteio.b = arrayList.get(indiB);
                if (indiA > indiB) {
                    arrayList.remove(indiA);
                    arrayList.remove(indiB);
                } else {
                    arrayList.remove(indiB);
                    arrayList.remove(indiA);
                }
            } else {
                int indiA = s.getRandomInt(0, arrayList.size());
                sorteio.a = arrayList.get(indiA);
                sorteio.b = null;
                arrayList.remove(indiA);
            }
            return sorteio;
        }
    }

}

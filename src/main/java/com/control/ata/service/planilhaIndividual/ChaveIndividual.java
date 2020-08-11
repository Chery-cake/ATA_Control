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
import java.util.List;
import java.util.Objects;

@Service
public class ChaveIndividual {

        @Autowired
    private PlanilhaChaveamentoIndividualRepository planilhaChaveamentoIndividualRepository;
    @Autowired
    private ChaveLutaIndividualRepository chaveLutaIndividualRepository;
    @Autowired
    private TituloRepository tituloRepository;

    public PlanilhaChaveamentoIndividual createPlanilha(RingueIndividual ringueIndividual,//todo verificar a categoria dos competidores e da planilha
            CategoriaCompeticao categoriaCompeticao) {
        ArrayList<Competidor> competidorArrayList = new ArrayList<>(ringueIndividual.getCompetidor());
        PlanilhaChaveamentoIndividual planilha = new PlanilhaChaveamentoIndividual(categoriaCompeticao,
                                                                                   ringueIndividual);
        planilha = planilhaChaveamentoIndividualRepository.save(planilha);
        planilha.setChaveLutaIndividual(createChave(competidorArrayList, planilha));
        return planilhaChaveamentoIndividualRepository.save(planilha);
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
                Objects.requireNonNull(chaveLutaIndividual2).setCompetidorBranco(competidor);
                chaveLutaIndividualRepository.save(chaveLutaIndividual2);
                Competidor competidor1;
                if (competidor == chaveLutaIndividual.getCompetidorBranco()) {
                    competidor1 = chaveLutaIndividual.getCompetidorVermelho();
                } else {
                    competidor1 = chaveLutaIndividual.getCompetidorBranco();
                }
                Objects.requireNonNull(chaveLutaIndividual3).setCompetidorBranco(competidor1);
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
            int posicao, int fase) {
        return chaveLutaIndividualRepository.save(new ChaveLutaIndividual(posicao, fase, compVer, compBra, planilha));
    }

    private List<ChaveLutaIndividual> createChave(ArrayList<Competidor> competidorArrayList,
            PlanilhaChaveamentoIndividual planilha) {
        List<ChaveLutaIndividual> list = new ArrayList<>();
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

                ArrayList<ArrayList<Competidor>> arrayList = new ArrayList<>();
                for (Competidor competidor : competidorArrayList) {
                    if (arrayList.isEmpty()) {
                        ArrayList<Competidor> competidors = new ArrayList<>();
                        competidors.add(competidor);
                        arrayList.add(competidors);
                    } else {
                        boolean adicionado = false;
                        for (ArrayList<Competidor> arrayList1 : arrayList) {
                            if (arrayList1.get(
                                    0).getPessoa().getInstrutor().getAcademia().getNome().equals(
                                    competidor.getPessoa().getInstrutor().getAcademia().getNome())) {
                                arrayList1.add(competidor);
                                adicionado = true;
                            }
                        }
                        if (!adicionado) {
                            ArrayList<Competidor> competidors = new ArrayList<>();
                            competidors.add(competidor);
                            arrayList.add(competidors);
                        }
                    }
                }

                while (arrayList.size() > 0) {
                    Sorteio sorteio = new Sorteio();
                    sorteio = sorteio.sorteio(arrayList);
                    list.add(setChave(sorteio.a, sorteio.b, planilha, i, fase));
                    i++;
                }
            } else if (competidorArrayList.size() == 2) {
                list.add(setChave(competidorArrayList.get(0), competidorArrayList.get(1), planilha, 1, 0));
            } else if (competidorArrayList.size() == 1) {
                list.add(setChave(competidorArrayList.get(0), null, planilha, 1, 0));
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

                ArrayList<ArrayList<Competidor>> arrayList = new ArrayList<>();
                for (Competidor competidor : competidorArrayList) {
                    if (arrayList.isEmpty()) {
                        ArrayList<Competidor> competidors = new ArrayList<>();
                        competidors.add(competidor);
                        arrayList.add(competidors);
                    } else {
                        boolean adicionado = false;
                        for (ArrayList<Competidor> arrayList1 : arrayList) {
                            if (arrayList1.get(
                                    0).getPessoa().getInstrutor().getAcademia().getNome().equals(
                                    competidor.getPessoa().getInstrutor().getAcademia().getNome())) {
                                arrayList1.add(competidor);
                                adicionado = true;
                            }
                        }
                        if (!adicionado) {
                            ArrayList<Competidor> competidors = new ArrayList<>();
                            competidors.add(competidor);
                            arrayList.add(competidors);
                        }
                    }
                }

                while (arrayList.size() > 0) {
                    Sorteio sorteio = new Sorteio();
                    sorteio = sorteio.sorteio(arrayList);
                    if (sorteio.b == null) {
                        list.add(setChave(sorteio.a, competidorTitulo, planilha, i, fase));
                        competidorTituloAdicionado = true;
                    } else {
                        list.add(setChave(sorteio.a, sorteio.b, planilha, i, fase));
                    }
                    i++;
                }
                if (!competidorTituloAdicionado) {
                    list.add(setChave(competidorTitulo, null, planilha, i, fase));
                }
            } else if (competidorArrayList.size() + 1 == 2) {
                list.add(setChave(competidorArrayList.get(0), competidorTitulo, planilha, 1, 0));
            } else if (competidorArrayList.size() == 0) {
                list.add(setChave(competidorTitulo, null, planilha, 1, 0));
            }
        }
        return list;
    }

    private Competidor sort(Collection<Competidor> competidorList,
            CategoriaCompeticao categoriaCompeticao) {//retorna o maior titulo
        return Singleton.getCompetidorTitulo(competidorList, categoriaCompeticao, tituloRepository);
    }

    private static class Sorteio {
        Competidor a;
        Competidor b;
        Singleton s = Singleton.getSingleton();

        public Sorteio sorteio(Iterable<?> iterables) {
            Sorteio sorteio = new Sorteio();

            ArrayList arrayList = (ArrayList) iterables;

            if (arrayList.size() > 1) {
                int indiCompetidores1 = s.getRandomInt(0, arrayList.size());
                ArrayList<Competidor> competidores1 = (ArrayList<Competidor>) arrayList.get(indiCompetidores1);
                ArrayList<Competidor> competidores2 = competidores1;
                int indiCompetidores2 = 0;

                while (competidores1 == competidores2) {
                    indiCompetidores2 = s.getRandomInt(0, arrayList.size());
                    competidores2 = (ArrayList<Competidor>) arrayList.get(indiCompetidores2);
                }

                sorteio.a = competidores1.get(s.getRandomInt(0, competidores1.size()));
                sorteio.b = competidores2.get(s.getRandomInt(0, competidores2.size()));

                competidores1.remove(sorteio.a);
                competidores2.remove(sorteio.b);

                if (indiCompetidores1 > indiCompetidores2) {
                    arrayList.remove(indiCompetidores1);
                    arrayList.remove(indiCompetidores2);
                } else {
                    arrayList.remove(indiCompetidores2);
                    arrayList.remove(indiCompetidores1);
                }

                if (!competidores1.isEmpty()) {
                    arrayList.add(competidores1);
                }
                if (!competidores2.isEmpty()) {
                    arrayList.add(competidores2);
                }
            } else {
                int indiCompetidores = 0;
                ArrayList<Competidor> competidores = (ArrayList<Competidor>) arrayList.get(indiCompetidores);

                if (competidores.size() > 1) {
                    int indiA = s.getRandomInt(0, competidores.size());
                    int indiB = indiA;

                    while (indiB == indiA) {
                        indiB = s.getRandomInt(0, competidores.size());
                    }
                    sorteio.a = competidores.get(indiA);
                    sorteio.b = competidores.get(indiB);

                    competidores.remove(sorteio.a);
                    competidores.remove(sorteio.b);

                    arrayList.remove(indiCompetidores);
                    if (!competidores.isEmpty()) {
                        arrayList.add(competidores);
                    }
                } else {
                    int indiA = s.getRandomInt(0, competidores.size());
                    sorteio.a = competidores.get(indiA);
                    sorteio.b = null;
                    competidores.remove(sorteio.a);

                    arrayList.remove(indiCompetidores);
                    if (!competidores.isEmpty()) {
                        arrayList.add(competidores);
                    }
                }
            }
            return sorteio;
        }
    }

}

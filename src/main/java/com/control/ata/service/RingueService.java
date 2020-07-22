package com.control.ata.service;

import com.control.ata.dao.RingueDAO;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Torneio;
import com.control.ata.repository.individual.RingueIndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RingueService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    @Autowired
    private RingueDAO ringueDAO;
    @Autowired
    private RingueIndividualRepository ringueIndividualRepository;

    public List<RingueIndividual> createRingueIndividual(Collection<Competidor> collection, Boolean fechado,
            Torneio torneio, Collection<CategoriaCompeticao> categoriaCompeticoes) {
        List<RingueIndividual> list = new ArrayList<>();

        ArrayList<Competidor> competidorArrayList = new ArrayList<>(collection);
        ArrayList<ArrayList<Competidor>> arrayList = new ArrayList<>();

        for (Competidor competidor : competidorArrayList) {
            if (arrayList.isEmpty()) {
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
            } else {

                int anoAtual = Integer.parseInt(dateFormat.format(new Date()));
                int anoNasc = Integer.parseInt(dateFormat.format(competidor.getPessoa().getDataNascimento()));
                int idade = anoAtual - anoNasc;

                if (idade >= 7 && idade < 9) {// 7 e 8
                    this.insereCompetidor(competidor, idade, arrayList);
                } else if (idade < 11) {// 9 e 10
                    this.insereCompetidor(competidor, idade, arrayList);
                } else if (idade < 13) {// 11 e 12
                    this.insereCompetidor(competidor, idade, arrayList);
                } else if (idade < 15) {// 13 e 14
                    this.insereCompetidor(competidor, idade, arrayList);
                } else if (idade < 18) {// 15 a 17
                    this.insereCompetidor(competidor, idade, arrayList);
                } else if (idade < 30) {// 18 a 29
                    this.insereCompetidor(competidor, idade, arrayList);
                } else if (idade < 40) {// 30 a 39
                    this.insereCompetidor(competidor, idade, arrayList);
                } else if (idade < 50) {// 40 a 49
                    this.insereCompetidor(competidor, idade, arrayList);
                } else if (idade < 60) {// 50 a 59
                    this.insereCompetidor(competidor, idade, arrayList);
                } else {// >= 60
                    this.insereCompetidor(competidor, idade, arrayList);
                }
            }
        }
//todo terminar a funcao para criar os ringues e balancealos. Ainda n tem nenhuma funcao especial no repositorio.
        int maxComp;
        if (fechado) {
            maxComp = 32;
        } else {
            maxComp = 16;
        }
        list = criaRingues(arrayList, fechado, maxComp, torneio, categoriaCompeticoes);

        return list;
    }

    private List<Competidor> criaArrayCompetidorRingue(ArrayList<ArrayList<Competidor>> arrayList) {
        List<Competidor> list = new ArrayList<>();

        ArrayList<Competidor> competidorArrayList = arrayList.get(0);
        arrayList.remove(competidorArrayList);

        Competidor competidorBase = competidorArrayList.get(0);
        String nivel = competidorBase.getNivel();
        list.add(competidorBase);

        for (Competidor competidor : competidorArrayList) {
            if (competidor.getNivel().equals(nivel)) {
                list.add(competidor);
            }
        }

        for (Competidor competidor : list) {
            competidorArrayList.remove(competidor);
        }
        if (!competidorArrayList.isEmpty()) {
            arrayList.add(competidorArrayList);
        }

        return this.sortAltura(list);
    }

    private List<RingueIndividual> criaRingues(ArrayList<ArrayList<Competidor>> arrayList, Boolean fechado,
            //todo precisa contar a quantidade de competidores por categoria
            Integer maxComp, Torneio torneio, Collection<CategoriaCompeticao> categoriaCompeticoes) {
        List<RingueIndividual> list = new ArrayList<>();

        while (!arrayList.isEmpty()) {
            ArrayList<Competidor> competidorArrayList = (ArrayList<Competidor>) criaArrayCompetidorRingue(arrayList);

            String nivel = competidorArrayList.get(0).getNivel();


            int anoAtual = Integer.parseInt(dateFormat.format(new Date()));
            int anoNasc = Integer.parseInt(
                    dateFormat.format(competidorArrayList.get(0).getPessoa().getDataNascimento()));
            int idade = anoAtual - anoNasc;
            String idadeRingue;
            if (idade >= 7 && idade < 9) {// 7 e 8
                idadeRingue = "7 e 8";
            } else if (idade < 11) {// 9 e 10
                idadeRingue = "9 e 10";
            } else if (idade < 13) {// 11 e 12
                idadeRingue = "11 e 12";
            } else if (idade < 15) {// 13 e 14
                idadeRingue = "13 e 14";
            } else if (idade < 18) {// 15 a 17
                idadeRingue = "15 a 17";
            } else if (idade < 30) {// 18 a 29
                idadeRingue = "18 a 29";
            } else if (idade < 40) {// 30 a 39
                idadeRingue = "30 a 39";
            } else if (idade < 50) {// 40 a 49
                idadeRingue = "40 a 49";
            } else if (idade < 60) {// 50 a 59
                idadeRingue = "50 a 59";
            } else {// >= 60
                idadeRingue = ">= 60";
            }

            if (ringueIndividualRepository.getAllByIdadeAndNivel(idadeRingue, nivel).isEmpty()) {//adicionar os ringues

                int quant = competidorArrayList.size();

                if (quant > maxComp) {

                    Integer quantCompRing = null;

                    for (int i = maxComp; i >= 10; i--) {
                        if (quant % i == 0) {
                            quantCompRing = i;
                            break;
                        }
                    }

                    if (quantCompRing != null) {
                        ArrayList<ArrayList<Competidor>> arrayListCompetidor = new ArrayList<>();
                        ArrayList<Competidor> arrayListAux = new ArrayList<>();
                        int aux = 0;

                        while (!competidorArrayList.isEmpty()) {
                            arrayListAux.add(competidorArrayList.get(aux));
                            aux++;
                            if (aux == 10) {
                                aux = 0;
                                arrayListCompetidor.add(arrayListAux);
                                arrayListAux.clear();
                            }
                        }

                        for (ArrayList<Competidor> competidores : arrayListCompetidor) {
                            list.add(new RingueIndividual(fechado, 0, idadeRingue, nivel, null, competidores, torneio,
                                                          categoriaCompeticoes));
                        }
                    } else {//todo terminar os cauculos pra pre-encher os ringues

                    }

                } else {
                    list.add(new RingueIndividual(fechado, 0, idadeRingue, nivel, null, competidorArrayList, torneio,
                                                  categoriaCompeticoes));
                }

            } else {// pegar os ringues e re-adicionalos

            }

        }

        return list;
    }

    private ArrayList<ArrayList<Competidor>> insereCompetidor(Competidor competidor, Integer idade,
            ArrayList<ArrayList<Competidor>> arrayList) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        int anoAtual = Integer.parseInt(dateFormat.format(new Date()));
        Boolean adicionado = false;

        for (ArrayList<Competidor> competidorArrayList : arrayList) {
            int anoNasc = Integer.parseInt(dateFormat.format(competidor.getPessoa().getDataNascimento()));
            int idadeA = anoAtual - anoNasc;
            if (idade >= 7 && idade < 9 && idadeA >= 7 && idadeA < 9) {// 7 e 8
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            } else if (idade < 11 && idadeA < 11) {// 9 e 10
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            } else if (idade < 13 && idadeA < 13) {// 11 e 12
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            } else if (idade < 15 && idadeA < 15) {// 13 e 14
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            } else if (idade < 18 && idadeA < 18) {// 15 a 17
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            } else if (idade < 30 && idadeA < 30) {// 18 a 29
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            } else if (idade < 40 && idadeA < 40) {// 30 a 39
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            } else if (idade < 50 && idadeA < 50) {// 40 a 49
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            } else if (idade < 60 && idadeA < 60) {// 50 a 59
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            } else if (idade >= 60 && idadeA >= 60) {// >= 60
                ArrayList<Competidor> aux = new ArrayList<>();
                aux.add(competidor);
                arrayList.add(aux);
                adicionado = true;
                break;
            }
        }

        if (!adicionado) {
            ArrayList<Competidor> aux = new ArrayList<>();
            aux.add(competidor);
            arrayList.add(aux);
        }

        return arrayList;
    }

    private List<Competidor> sortAltura(Collection<Competidor> collection) {

        Competidor[] competidorArray = collection.toArray(new Competidor[0]);

        for (int i = 0; i < competidorArray.length; i++) {
            for (int j = 0; j < competidorArray.length; j++) {

                Competidor competidor1 = competidorArray[j];

                Competidor competidor2 = null;
                if (j + 1 != competidorArray.length) {
                    competidor2 = competidorArray[j + 1];
                }

                if (competidor2 != null) {
                    if (competidor1.getAltura() < competidor2.getAltura()) {
                        competidorArray[j] = competidor2;
                        competidorArray[j + 1] = competidor1;
                    } else {
                        competidorArray[j] = competidor1;
                        competidorArray[j + 1] = competidor2;
                    }
                }
            }
        }
        return Arrays.asList(competidorArray);
    }

}

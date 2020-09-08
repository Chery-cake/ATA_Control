package com.control.ata.service;

import com.control.ata.dao.RingueDAO;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.time.Time;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Torneio;
import com.control.ata.repository.individual.RingueIndividualRepository;
import com.control.ata.repository.time.RingueTimeRepository;
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
    @Autowired
    private RingueTimeRepository ringueTimeRepository;

    public List<RingueTime> createRingueTime(Collection<Time> collection, Boolean fechado, Torneio torneio,
            CategoriaCompeticao categoriaCompeticao) {
        List<RingueTime> list = new ArrayList<>();

        ArrayList<Time> timeArrayList = new ArrayList<>(collection);
        ArrayList<Time> timeJuniorArrayList = new ArrayList<>();
        for (Time time : timeArrayList) {
            if (time.getJunior()) {
                timeJuniorArrayList.add(time);
            }
        }
        if (!timeJuniorArrayList.isEmpty()) {
            for (Time time : timeJuniorArrayList) {
                timeArrayList.remove(time);
            }
        }
        if (!ringueTimeRepository.getAllByFechadoAndTorneioAndCategoriaCompeticao(fechado, torneio,
                                                                                  categoriaCompeticao).isEmpty()) {
            for (RingueTime ringueTime : ringueTimeRepository.findAll()) {
                ArrayList<Time> times = new ArrayList<>(ringueTime.getTime());
                if (times.get(0).getJunior()) {
                    if (!timeJuniorArrayList.isEmpty()) {
                        times.addAll(timeJuniorArrayList);
                    }
                } else {
                    times.addAll(timeArrayList);
                }
                ringueTime.setTime(times);
                list.add(ringueDAO.save(ringueTime));
            }
        } else {
            if (!timeJuniorArrayList.isEmpty()) {
                list.add(ringueDAO.save(
                        new RingueTime(fechado, 0, null, timeJuniorArrayList, torneio, categoriaCompeticao)));
            }
            list.add(
                    ringueDAO.save(
                            new RingueTime(fechado, 0, null, timeArrayList, torneio, categoriaCompeticao)));
        }
        return list;
    }

    public List<RingueIndividual> createRingueIndividual(Collection<Competidor> collection, Boolean fechado,
            Torneio torneio, Collection<CategoriaCompeticao> categoriaCompeticoes) {
        List<RingueIndividual> list;

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
        int maxComp;
        if (fechado) {
            maxComp = 32;
        } else {
            maxComp = 16;
        }
        list = criaRingueIndividuais(arrayList, fechado, maxComp, torneio, categoriaCompeticoes);
        return list;
    }

    private List<RingueIndividual> criaRingueIndividuais(ArrayList<ArrayList<Competidor>> arrayList, Boolean fechado,
            Integer maxComp, Torneio torneio, Collection<CategoriaCompeticao> categoriaCompeticoes) {
        List<RingueIndividual> list = new ArrayList<>();

        while (!arrayList.isEmpty()) {
            ArrayList<Competidor> competidorArrayList = new ArrayList<>(arrayList.get(0));
            arrayList.remove(competidorArrayList);
            competidorArrayList = new ArrayList<>(sortAltura(competidorArrayList));

            Integer nivel = competidorArrayList.get(0).getNivel();

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

            if (!ringueIndividualRepository.getAllByIdadeAndNivel(idadeRingue, nivel).isEmpty()) {
                for (RingueIndividual ringueIndividual : ringueIndividualRepository.getAllByIdadeAndNivel(idadeRingue,
                                                                                                          nivel)) {
                    for (Competidor competidor : ringueIndividual.getCompetidor()) {
                        if (!competidorArrayList.contains(competidor)) {
                            competidorArrayList.add(competidor);
                        }
                    }
                }
                for (int i = 0; i < competidorArrayList.size(); i++) {
                    if (i + 1 < competidorArrayList.size()) {
                        if (competidorArrayList.get(i).equals(competidorArrayList.get(i + 1))) {
                            competidorArrayList.remove(competidorArrayList.get(i + 1));
                        }
                    }
                }
                ringueIndividualRepository.deleteAll(
                        ringueIndividualRepository.getAllByIdadeAndNivel(idadeRingue, nivel));
                competidorArrayList = new ArrayList<>(sortAltura(competidorArrayList));
            }
            list = insereRingueIndividuais(competidorArrayList, maxComp, fechado, idadeRingue, nivel, torneio,
                                           categoriaCompeticoes);
        }
        return list;
    }

    private List<RingueIndividual> insereRingueIndividuais(ArrayList<Competidor> competidorArrayList, Integer maxComp,
            Boolean fechado, String idadeRingue, Integer nivel, Torneio torneio,
            Collection<CategoriaCompeticao> categoriaCompeticoes) {
        List<RingueIndividual> list = new ArrayList<>();
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
                ArrayList<Competidor> arrayListAux = new ArrayList<>();
                int aux = 0;
                while (!competidorArrayList.isEmpty()) {
                    arrayListAux.add(competidorArrayList.get(aux));
                    aux++;
                    if (aux == quantCompRing) {
                        aux = 0;
                        for (Competidor competidor : arrayListAux) {
                            competidorArrayList.remove(competidor);
                        }
                        list.add(ringueDAO.save(
                                new RingueIndividual(fechado, 0, idadeRingue, nivel, null, arrayListAux, torneio,
                                                     categoriaCompeticoes)));
                        arrayListAux.clear();
                    }
                }
            } else {

                float quantRing = (float) quant / maxComp;
                float aux = Math.round(quantRing);
                if (aux == quant / maxComp) {
                    aux++;
                }

                float quantCompAux = maxComp / aux;
                int quantComp = Math.round(quantCompAux);
                if (quantComp < quantCompAux) {
                    quantComp++;
                }

                int aux1 = maxComp;

                while (true) {
                    if (quantComp < aux1) {
                        quantComp++;
                        aux1--;
                    } else if (aux1 < quantComp) {
                        quantComp--;
                        aux1++;
                    } else if (quantComp == aux1) {
                        break;
                    }
                    if ((quantComp + 1 > aux1 - 1) && (aux1 + 1 > quantComp - 1)) {
                        break;
                    }
                }

                float v = ((aux - 2) * maxComp) + quantComp + aux1;
                if (v < competidorArrayList.size()) {
                    aux1 += competidorArrayList.size() - v;
                } else if (v > competidorArrayList.size()) {
                    aux1 -= v - competidorArrayList.size();
                }

                while (aux1 > maxComp) {
                    aux1--;
                    quantComp++;
                }

                ArrayList<Competidor> arrayListAux = new ArrayList<>();
                int count = 0;
                boolean ringEsp1 = false;
                boolean ringEsp2 = false;

                while (!competidorArrayList.isEmpty()) {
                    arrayListAux.add(competidorArrayList.get(count));
                    count++;
                    if (count == maxComp && ringEsp1 && ringEsp2) {
                        for (Competidor competidor : arrayListAux) {
                            competidorArrayList.remove(competidor);
                        }
                        count = 0;
                        list.add(ringueDAO.save(
                                new RingueIndividual(fechado, 0, idadeRingue, nivel, null, arrayListAux, torneio,
                                                     categoriaCompeticoes)));
                        arrayListAux.clear();
                    } else if (count == quantComp && !ringEsp1) {
                        for (Competidor competidor : arrayListAux) {
                            competidorArrayList.remove(competidor);
                        }
                        count = 0;
                        list.add(ringueDAO.save(
                                new RingueIndividual(fechado, 0, idadeRingue, nivel, null, arrayListAux, torneio,
                                                     categoriaCompeticoes)));
                        ringEsp1 = true;
                        arrayListAux.clear();
                    } else if (count == aux1 && !ringEsp2) {
                        for (Competidor competidor : arrayListAux) {
                            competidorArrayList.remove(competidor);
                        }
                        count = 0;
                        ringEsp2 = true;
                        list.add(ringueDAO.save(
                                new RingueIndividual(fechado, 0, idadeRingue, nivel, null, arrayListAux, torneio,
                                                     categoriaCompeticoes)));
                        arrayListAux.clear();
                    }
                }
            }
        } else {
            list.add(ringueDAO.save(
                    new RingueIndividual(fechado, 0, idadeRingue, nivel, null, competidorArrayList, torneio,
                                         categoriaCompeticoes)));
        }
        return list;
    }

    private ArrayList<ArrayList<Competidor>> insereCompetidor(Competidor competidor, Integer idade,
            ArrayList<ArrayList<Competidor>> arrayList) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        int anoAtual = Integer.parseInt(dateFormat.format(new Date()));
        boolean adicionado = false;

        for (ArrayList<Competidor> competidorArrayList : arrayList) {
            int anoNasc = Integer.parseInt(
                    dateFormat.format(competidorArrayList.get(0).getPessoa().getDataNascimento()));
            int idadeA = anoAtual - anoNasc;
            if (idade >= 7 && idade < 9 && idadeA >= 7 && idadeA < 9) {// 7 e 8
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 11 && idadeA < 11) {// 9 e 10
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 13 && idadeA < 13) {// 11 e 12
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 15 && idadeA < 15) {// 13 e 14
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 18 && idadeA < 18) {// 15 a 17
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 30 && idadeA < 30) {// 18 a 29
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 40 && idadeA < 40) {// 30 a 39
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 50 && idadeA < 50) {// 40 a 49
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 60 && idadeA < 60) {// 50 a 59
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade >= 60 && idadeA >= 60) {// >= 60
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
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

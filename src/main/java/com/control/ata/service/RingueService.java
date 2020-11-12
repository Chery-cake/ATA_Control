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
import com.control.ata.repository.tipo_pessoa.CompetidorRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
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
    @Autowired
    private CategoriaCompeticaoRepository categoriaCompeticaoRepository;
    @Autowired
    private CompetidorRepository competidorRepository;

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

    public List<RingueIndividual> createRingueIndividual(Torneio torneio) {
        List<RingueIndividual> list = new ArrayList<>();

        ArrayList<Competidor> competidorArrayList = new ArrayList<>(competidorRepository.getAllByTorneio(torneio));
        ArrayList<ArrayList<Competidor>> arrayListCompetidorT = new ArrayList<>();
        ArrayList<ArrayList<Competidor>> arrayListCompetidorF = new ArrayList<>();

        for (Competidor competidor : competidorArrayList) {

            if(competidor.getPessoa().getGenero()){
                if (arrayListCompetidorT.isEmpty()) {
                    ArrayList<Competidor> aux = new ArrayList<>();
                    aux.add(competidor);
                    arrayListCompetidorT.add(aux);
                } else {

                    int anoAtual = Integer.parseInt(dateFormat.format(new Date()));
                    int anoNasc = Integer.parseInt(dateFormat.format(competidor.getPessoa().getDataNascimento()));
                    int idade = anoAtual - anoNasc;

                    this.insereCompetidor(competidor, idade, arrayListCompetidorT);
                }
            }else {
                if (arrayListCompetidorF.isEmpty()) {
                    ArrayList<Competidor> aux = new ArrayList<>();
                    aux.add(competidor);
                    arrayListCompetidorF.add(aux);
                } else {

                    int anoAtual = Integer.parseInt(dateFormat.format(new Date()));
                    int anoNasc = Integer.parseInt(dateFormat.format(competidor.getPessoa().getDataNascimento()));
                    int idade = anoAtual - anoNasc;

                    this.insereCompetidor(competidor, idade, arrayListCompetidorF);
                }
            }
        }

        ArrayList<RingueIndividual> ringueIndividualArrayList = new ArrayList<>(
                ringueIndividualRepository.getAllByTorneio(torneio));

        ArrayList<RingueIndividual> arrayListRingueT = new ArrayList<>();
        ArrayList<RingueIndividual> arrayListRingueF = new ArrayList<>();

        ArrayList<RingueIndividual> arrayListRingueFechadoT = new ArrayList<>();
        ArrayList<RingueIndividual> arrayListRingueFechadoF = new ArrayList<>();

        for (RingueIndividual ringueIndividual : ringueIndividualArrayList) {
            if (ringueIndividual.getFechado()) {
                if(ringueIndividual.getGenero()){
                    arrayListRingueFechadoT.add(ringueIndividual);
                }else {
                    arrayListRingueFechadoF.add(ringueIndividual);
                }
            } else {
                if (ringueIndividual.getGenero()){
                    arrayListRingueT.add(ringueIndividual);
                }else {
                    arrayListRingueF.add(ringueIndividual);
                }
            }
        }//todo fazer sistema para ringues fechados // meio pronto falta aa parte da inscricao dos competidores
        //todo arrumar sistema pra genero // aparenta estar funcionando
        //todo arrumar sistema de categorias // aparenta estar funcionando

        if (!arrayListRingueT.isEmpty()) {
            list.addAll(insetCompetidoresRinguesIndividual(arrayListCompetidorT, arrayListRingueT));
        }
        if (!arrayListRingueF.isEmpty()) {
            list.addAll(insetCompetidoresRinguesIndividual(arrayListCompetidorF, arrayListRingueF));
        }

        if(!arrayListRingueFechadoT.isEmpty()){
            list.addAll(insetCompetidoresRinguesIndividual(arrayListCompetidorT, arrayListRingueFechadoT));
        }
        if(!arrayListRingueFechadoF.isEmpty()){
            list.addAll(insetCompetidoresRinguesIndividual(arrayListCompetidorF, arrayListRingueFechadoF));
        }

        return list;
    }

    private List<RingueIndividual> insetCompetidoresRinguesIndividual(
            ArrayList<ArrayList<Competidor>> arrayListCompetidor,
            ArrayList<RingueIndividual> arrayListRingue) {
        List<RingueIndividual> list = new ArrayList<>();

        while (!arrayListCompetidor.isEmpty()) {
            ArrayList<Competidor> competidorArrayList = new ArrayList<>(arrayListCompetidor.get(0));
            arrayListCompetidor.remove(competidorArrayList);
            competidorArrayList = new ArrayList<>(sortAltura(competidorArrayList));

            int anoAtual = Integer.parseInt(dateFormat.format(new Date()));
            int anoNasc = Integer.parseInt(
                    dateFormat.format(competidorArrayList.get(0).getPessoa().getDataNascimento()));
            int idade = anoAtual - anoNasc;
            int idadeRingue;
            if (idade >= 7 && idade < 9) {// 7 e 8
                idadeRingue = 1;
            } else if (idade < 11) {// 9 e 10
                idadeRingue = 2;
            } else if (idade < 13) {// 11 e 12
                idadeRingue = 3;
            } else if (idade < 15) {// 13 e 14
                idadeRingue = 4;
            } else if (idade < 18) {// 15 a 17
                idadeRingue = 5;
            } else if (idade < 30) {// 18 a 29
                idadeRingue = 6;
            } else if (idade < 40) {// 30 a 39
                idadeRingue = 7;
            } else if (idade < 50) {// 40 a 49
                idadeRingue = 8;
            } else if (idade < 60) {// 50 a 59
                idadeRingue = 9;
            } else {// >= 60
                idadeRingue = 10;
            }

            for (RingueIndividual ringueIndividual : arrayListRingue) {
                if (idadeRingue == ringueIndividual.getIdade()) {
                    ArrayList<Competidor> ringComps = new ArrayList<>();
                    for (Competidor competidor: competidorArrayList){
                        if(ringueIndividual.getNivel().equals(competidor.getNivel())){
                            for (CategoriaCompeticao categoriaCompeticao : categoriaCompeticaoRepository.getAllByCompetidor(competidor.getId())){
                                boolean add = false;
                                for (CategoriaCompeticao categoriaCompeticaoRing: categoriaCompeticaoRepository.getAllByRingueIndividual(ringueIndividual.getId())){
                                    if (categoriaCompeticao.getId().equals(categoriaCompeticaoRing.getId())){
                                        ringComps.add(competidor);
                                        add = true;
                                        break;
                                    }
                                }
                                if (add){
                                    break;
                                }
                            }
                        }
                    }
                    list.addAll(insereRingueIndividuais(ringComps, ringueIndividual));
                }
            }
        }

        return list;
    }

    private List<RingueIndividual> insereRingueIndividuais(ArrayList<Competidor> competidorArrayList,//todo arrumar a criacao do array de competidores
            RingueIndividual ringueIndividual) {

        System.out.println(competidorArrayList);

        int maxComp;
        if (ringueIndividual.getFechado()) {
            maxComp = 32;
        } else {
            maxComp = 16;
        }

        List<RingueIndividual> list = new ArrayList<>();

        int quant = competidorArrayList.size();
        if (quant > maxComp) {
            ArrayList<ArrayList<Competidor>> arrayListComp = new ArrayList<>();
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
                            if (competidorArrayList.contains(competidor)){
                                competidorArrayList.remove(competidor);
                            }
                            competidorArrayList.remove(competidor);
                        }
                        arrayListComp.add(arrayListAux);
//                        list.add(ringueDAO.save(
//                                new RingueIndividual(fechado, 0, idadeRingue, nivel, null, arrayListAux, torneio,
//                                                     categoriaCompeticoes)));
                        arrayListAux = new ArrayList<>();
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
                        arrayListComp.add(arrayListAux);
//                        list.add(ringueDAO.save(
//                                new RingueIndividual(fechado, 0, idadeRingue, nivel, null, arrayListAux, torneio,
//                                                     categoriaCompeticoes)));
                        arrayListAux = new ArrayList<>();
                    } else if (count == quantComp && !ringEsp1) {
                        for (Competidor competidor : arrayListAux) {
                            competidorArrayList.remove(competidor);
                        }
                        count = 0;
                        arrayListComp.add(arrayListAux);
//                        list.add(ringueDAO.save(
//                                new RingueIndividual(fechado, 0, idadeRingue, nivel, null, arrayListAux, torneio,
//                                                     categoriaCompeticoes)));
                        ringEsp1 = true;
                        arrayListAux = new ArrayList<>();
                    } else if (count == aux1 && !ringEsp2) {
                        for (Competidor competidor : arrayListAux) {
                            competidorArrayList.remove(competidor);
                        }
                        count = 0;
                        ringEsp2 = true;
                        arrayListComp.add(arrayListAux);
//                        list.add(ringueDAO.save(
//                                new RingueIndividual(fechado, 0, idadeRingue, nivel, null, arrayListAux, torneio,
//                                                     categoriaCompeticoes)));
                        arrayListAux = new ArrayList<>();
                    }
                }
            }

            quant = arrayListComp.size();
            if (quant > 1) {
                ArrayList<RingueIndividual> ringuesRodada = (ArrayList<RingueIndividual>) ringueIndividualRepository.getAllByRodadaJuiz(
                        ringueIndividual.getRodadaJuiz());

                if (ringuesRodada.size() > 1) {

                    for (RingueIndividual aux : ringuesRodada) {
                        if (aux.getNumeroRodada() > ringueIndividual.getNumeroRodada()) {
                            aux.setNumeroRodada(aux.getNumeroRodada() + quant);
                            ringueDAO.save(aux);
                        }
                    }

                    for (int i = 0; i < quant; i++) {
                        if (i == 0) {
                            ringueIndividual.setCompetidor(arrayListComp.get(i));
                            list.add(ringueDAO.save(ringueIndividual));
                        } else {
                            RingueIndividual ringueIndividual1 = new RingueIndividual(ringueIndividual.getGenero(),
                                                                                      ringueIndividual.getFechado(),
                                                                                      ringueIndividual.getNumeroRingue(),
                                                                                      ringueIndividual.getNumeroRodada() + i,
                                                                                      ringueIndividual.getIdade(),
                                                                                      ringueIndividual.getNivel(),
                                                                                      ringueIndividual.getJuiz(),
                                                                                      ringueIndividual.getTorneio(),
                                                                                      ringueIndividual.getCategoriaCompeticao(),
                                                                                      ringueIndividual.getRodadaJuiz());
                            ringueIndividual1.setCompetidor(arrayListComp.get(i));
                            list.add(ringueDAO.save(ringueIndividual1));
                        }
                    }

                } else {
                    for (int i = 0; i < quant; i++) {
                        if (i == 0) {
                            ringueIndividual.setCompetidor(arrayListComp.get(i));
                            list.add(ringueDAO.save(ringueIndividual));
                        } else {
                            RingueIndividual ringueIndividual1 = new RingueIndividual(ringueIndividual.getGenero(),
                                                                                      ringueIndividual.getFechado(),
                                                                                      ringueIndividual.getNumeroRingue(),
                                                                                      ringueIndividual.getNumeroRodada() + i,
                                                                                      ringueIndividual.getIdade(),
                                                                                      ringueIndividual.getNivel(),
                                                                                      ringueIndividual.getJuiz(),
                                                                                      ringueIndividual.getTorneio(),
                                                                                      ringueIndividual.getCategoriaCompeticao(),
                                                                                      ringueIndividual.getRodadaJuiz());
                            ringueIndividual1.setCompetidor(arrayListComp.get(i));
                            list.add(ringueDAO.save(ringueIndividual1));
                        }
                    }
                }
            } else {
                ringueIndividual.setCompetidor(arrayListComp.get(0));
                list.add(ringueDAO.save(ringueIndividual));
            }

        } else {
            ringueIndividual.setCompetidor(competidorArrayList);
            list.add(ringueDAO.save(ringueIndividual));
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
            if (idade >= 7 && idade < 9 && idadeA >= 7 && idadeA < 9 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// 7 e 8
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 11 && idadeA < 11 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// 9 e 10
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 13 && idadeA < 13 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// 11 e 12
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 15 && idadeA < 15 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// 13 e 14
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 18 && idadeA < 18 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// 15 a 17
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 30 && idadeA < 30 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// 18 a 29
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 40 && idadeA < 40 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// 30 a 39
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 50 && idadeA < 50 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// 40 a 49
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade < 60 && idadeA < 60 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// 50 a 59
                arrayList.remove(competidorArrayList);
                competidorArrayList.add(competidor);
                arrayList.add(competidorArrayList);
                adicionado = true;
                break;
            } else if (idade >= 60 && idadeA >= 60 && competidorArrayList.get(0).getNivel().equals(
                    competidor.getNivel())) {// >= 60
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

package com.control.ata;

import com.control.ata.model.time.Time;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Titulo;
import com.control.ata.repository.torneio.TituloRepository;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class Singleton {

    private static Singleton single_instance = null;

    private Singleton() {

    }

    public static Singleton getSingleton() {
        if (single_instance == null) {
            single_instance = new Singleton();
        }
        return single_instance;
    }

    public static Competidor getCompetidorTitulo(Collection<Competidor> competidorList,
            CategoriaCompeticao categoriaCompeticao, TituloRepository tituloRepository) {
        Competidor[] competidorArray = competidorList.toArray(new Competidor[0]);
        Competidor[] competidorArrayAux = new Competidor[2];

        for (int i = 0; i < competidorArray.length; i++) {
            for (int j = 0; j < competidorArray.length; j++) {
                Titulo titulo1 = tituloRepository.getByCompetidorAndCategoriaCompeticao(competidorArray[j],
                                                                                        categoriaCompeticao);
                Titulo titulo2 = null;
                if (j + 1 != competidorArray.length) {
                    titulo2 = tituloRepository.getByCompetidorAndCategoriaCompeticao(competidorArray[j + 1],
                                                                                     categoriaCompeticao);
                }

                if ((titulo1 != null) && (titulo2 != null)) {
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
                } else if ((titulo1 != null) && (j + 1 != competidorArray.length)) {
                    competidorArrayAux[0] = competidorArray[j];
                    competidorArrayAux[1] = competidorArray[j + 1];
                    competidorArray[j] = competidorArrayAux[1];
                    competidorArray[j + 1] = competidorArrayAux[0];
                }
            }
        }
        return competidorArray[competidorArray.length - 1];
    }

    public static Time getTimeTitulo(Collection<Time> timeList,
            CategoriaCompeticao categoriaCompeticao, TituloRepository tituloRepository) {
        Time[] timeArray = timeList.toArray(new Time[0]);
        Time[] timeArrayAux = new Time[2];

        for (int i = 0; i < timeArray.length; i++) {
            for (int j = 0; j < timeArray.length; j++) {
                Titulo titulo1 = tituloRepository.getByTimeAndCategoriaCompeticao(timeArray[j],
                                                                                  categoriaCompeticao);
                Titulo titulo2 = null;
                if (j + 1 != timeArray.length) {
                    titulo2 = tituloRepository.getByTimeAndCategoriaCompeticao(timeArray[j + 1],
                                                                               categoriaCompeticao);
                }
                if ((titulo1 != null) && (titulo2 != null)) {
                    if ((titulo1.getCategoriaTitulo().getPrioridade() > titulo2.getCategoriaTitulo().getPrioridade()) && (titulo1.getAno() > titulo2.getAno())) {
                        timeArrayAux[0] = timeArray[j];
                        timeArrayAux[1] = timeArray[j + 1];
                        timeArray[j] = timeArrayAux[1];
                        timeArray[j + 1] = timeArrayAux[0];
                    } else if (titulo1.getAno() > titulo2.getAno()) {
                        timeArrayAux[0] = timeArray[j];
                        timeArrayAux[1] = timeArray[j + 1];
                        timeArray[j] = timeArrayAux[1];
                        timeArray[j + 1] = timeArrayAux[0];
                    } else if (titulo1.getCategoriaTitulo().getPrioridade() > titulo2.getCategoriaTitulo().getPrioridade()) {
                        timeArrayAux[0] = timeArray[j];
                        timeArrayAux[1] = timeArray[j + 1];
                        timeArray[j] = timeArrayAux[1];
                        timeArray[j + 1] = timeArrayAux[0];
                    }
                } else if ((titulo1 != null) && (j + 1 != timeArray.length)) {
                    timeArrayAux[0] = timeArray[j];
                    timeArrayAux[1] = timeArray[j + 1];
                    timeArray[j] = timeArrayAux[1];
                    timeArray[j + 1] = timeArrayAux[0];
                }
            }
        }
        return timeArray[timeArray.length - 1];
    }

    public Integer getRandomInt(Integer mim, Integer max) {
        return ThreadLocalRandom.current().nextInt(mim, max);
    }

}

package com.control.ata.service.planilhaTime;

import com.control.ata.Singleton;
import com.control.ata.model.time.PlanilhaListaTime;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.time.Time;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.time.PlanilhaListaTimeRepository;
import com.control.ata.repository.time.RingueTimeRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ListaTime {

    Singleton s = Singleton.getSingleton();

    @Autowired
    private RingueTimeRepository ringueTimeRepository;
    @Autowired
    private PlanilhaListaTimeRepository planilhaListaTimeRepository;
    @Autowired
    private TituloRepository tituloRepository;

    public List<PlanilhaListaTime> createPlanilha(RingueTime ringueTime, CategoriaCompeticao categoriaCompeticao) {
        List<PlanilhaListaTime> list = new ArrayList<>();

        ArrayList<Time> timeArrayList = new ArrayList<>(ringueTime.getTime());
        Time timeTitulo = null;

        if (!ringueTime.getFechado()) {
            timeTitulo = this.sort(timeArrayList, categoriaCompeticao);
            timeArrayList.remove(timeTitulo);
        }

        while (timeArrayList.size() > 0) {
            int indi = s.getRandomInt(0, timeArrayList.size());
            list.add(planilhaListaTimeRepository.save(
                    new PlanilhaListaTime(timeArrayList.get(indi), ringueTime, categoriaCompeticao)));
            timeArrayList.remove(indi);
        }

        if (timeTitulo != null) {
            list.add(planilhaListaTimeRepository.save(
                    new PlanilhaListaTime(timeTitulo, ringueTime, categoriaCompeticao)));
        }

        return list;
    }

    public PlanilhaListaTime setPlanilha(PlanilhaListaTime planilha) {
        return planilhaListaTimeRepository.save(planilha);
    }

    private Time sort(Collection<Time> timeList, CategoriaCompeticao categoriaCompeticao) {
        return Singleton.getTimeTitulo(timeList, categoriaCompeticao, tituloRepository);
    }

}

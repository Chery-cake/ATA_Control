package com.control.ata.service.planilhaTime;

import com.control.ata.Singleton;
import com.control.ata.model.time.ChaveListaTime;
import com.control.ata.model.time.PlanilhaListaTime;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.time.Time;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.time.ChaveListaTimeRepository;
import com.control.ata.repository.time.PlanilhaListaTimeRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ListaTime {

    Singleton s = Singleton.getSingleton();

    @Autowired
    private PlanilhaListaTimeRepository planilhaListaTimeRepository;
    @Autowired
    private TituloRepository tituloRepository;
    @Autowired
    private ChaveListaTimeRepository chaveListaTimeRepository;

    public PlanilhaListaTime createPlanilha(RingueTime ringueTime, CategoriaCompeticao categoriaCompeticao) {
        ArrayList<Time> timeArrayList = new ArrayList<>(ringueTime.getTime());
        Time timeTitulo = null;
        PlanilhaListaTime planilhaListaTime = new PlanilhaListaTime(ringueTime, categoriaCompeticao);

        planilhaListaTime = planilhaListaTimeRepository.save(planilhaListaTime);

        if (!ringueTime.getFechado()) {
            timeTitulo = this.sort(timeArrayList, categoriaCompeticao);
            timeArrayList.remove(timeTitulo);
        }

        ArrayList<ChaveListaTime> list = new ArrayList<>();

        while (timeArrayList.size() > 0) {
            int indi = s.getRandomInt(0, timeArrayList.size());
            list.add(chaveListaTimeRepository.save(new ChaveListaTime(timeArrayList.get(indi), planilhaListaTime)));
            timeArrayList.remove(indi);
        }

        if (timeTitulo != null) {
            list.add(chaveListaTimeRepository.save(new ChaveListaTime(timeTitulo, planilhaListaTime)));
        }

        planilhaListaTime.setChaveListaTimeList(list);

        return planilhaListaTimeRepository.save(planilhaListaTime);
    }

    public ChaveListaTime setChavePlanilha(ChaveListaTime chaveListaTime) {
        return chaveListaTimeRepository.save(chaveListaTime);
    }

    private Time sort(Collection<Time> timeList, CategoriaCompeticao categoriaCompeticao) {
        return Singleton.getTimeTitulo(timeList, categoriaCompeticao, tituloRepository);
    }

}

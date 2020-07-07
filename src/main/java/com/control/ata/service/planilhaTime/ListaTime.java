package com.control.ata.service.planilhaTime;

import com.control.ata.model.time.PlanilhaListaTime;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.time.Time;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.repository.time.PlanilhaListaTimeRepository;
import com.control.ata.repository.time.RingueTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ListaTime {

    @Autowired
    private RingueTimeRepository ringueTimeRepository;
    @Autowired
    private PlanilhaListaTimeRepository planilhaListaTimeRepository;

    public void createPlanilha(RingueTime ringueTime, CategoriaCompeticao categoriaCompeticao) {
        ArrayList<Time> timeArrayList = (ArrayList<Time>) ringueTimeRepository.getCompetidoresByRingueTime(ringueTime);
        for (Time time : timeArrayList) {
            planilhaListaTimeRepository.save(new PlanilhaListaTime(time, ringueTime, categoriaCompeticao));
        }
    }

    public void setPlanilha(PlanilhaListaTime planilha) {
        planilhaListaTimeRepository.save(planilha);
    }

}

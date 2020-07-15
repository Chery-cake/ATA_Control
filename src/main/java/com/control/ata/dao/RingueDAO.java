package com.control.ata.dao;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.time.RingueTime;
import com.control.ata.repository.individual.RingueIndividualRepository;
import com.control.ata.repository.time.RingueTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RingueDAO {

    @Autowired
    private RingueIndividualRepository ringueIndividualRepository;
    @Autowired
    private RingueTimeRepository ringueTimeRepository;

    public RingueIndividual save(RingueIndividual ringueIndividual) {
        RingueIndividual aux = new RingueIndividual(ringueIndividual.getFechado(), ringueIndividual.getNumero(), null,
                                                    null, ringueIndividual.getTorneio(), null);
        aux = ringueIndividualRepository.save(aux);
        aux.setCompetidor(ringueIndividual.getCompetidor());
        aux.setJuiz(ringueIndividual.getJuiz());
        aux.setCategoriaCompeticao(ringueIndividual.getCategoriaCompeticao());
        return ringueIndividualRepository.save(aux);
    }

    public RingueTime save(RingueTime ringueTime) {
        RingueTime aux = new RingueTime(ringueTime.getFechado(), ringueTime.getNumero(), null, null,
                                        ringueTime.getTorneio(), null);
        aux = ringueTimeRepository.save(aux);
        aux.setJuiz(ringueTime.getJuiz());
        aux.setTime(ringueTime.getTime());
        aux.setCategoriaCompeticao(ringueTime.getCategoriaCompeticao());
        return ringueTimeRepository.save(aux);
    }

    private <T> T saveObj(Object obj) {
        Object o = null;
        if (RingueIndividual.class.equals(obj.getClass())) {
            o = this.save((RingueIndividual) obj);
        } else if (RingueTime.class.equals(obj.getClass())) {
            o = this.save((RingueTime) obj);
        }
        return (T) o;
    }

    public <T> List<T> saveAll(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (Object o : iterable) {
            list.add(this.saveObj(o));
        }
        return list;
    }

    public void delete(RingueIndividual ringueIndividual) {
        ringueIndividualRepository.delete(ringueIndividual);
    }

    public void deleteAll(Iterable<RingueIndividual> iterable) {
        for (RingueIndividual ringue : iterable) {
            this.delete(ringue);
        }
    }

}

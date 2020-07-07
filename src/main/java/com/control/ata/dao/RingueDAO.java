package com.control.ata.dao;

import com.control.ata.model.individual.RingueIndividual;
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
    //TODO fazer o DAO do ringueTime

    public RingueIndividual save(RingueIndividual ringueIndividual) {
        RingueIndividual aux = new RingueIndividual(ringueIndividual.getFechado(), ringueIndividual.getNumero(), null,
                                                    null, ringueIndividual.getTorneio(), null);
        aux = ringueIndividualRepository.save(aux);
        aux.setCompetidor(ringueIndividual.getCompetidor());
        aux.setJuiz(ringueIndividual.getJuiz());
        aux.setCategoriaCompeticao(ringueIndividual.getCategoriaCompeticao());
        return ringueIndividualRepository.save(aux);
    }

    public List<RingueIndividual> saveAll(Iterable<RingueIndividual> iterable) {
        List<RingueIndividual> individuals = new ArrayList<>();
        for (RingueIndividual ringue : iterable) {
            individuals.add(this.save(ringue));
        }
        return individuals;
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

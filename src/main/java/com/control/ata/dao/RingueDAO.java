package com.control.ata.dao;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.repository.individual.RingueIndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RingueDAO {

    @Autowired
    private RingueIndividualRepository ringueIndividualRepository;

    public RingueIndividual save(RingueIndividual ringueIndividual) {
        if (ringueIndividual.getId() == null) {
            RingueIndividual aux = new RingueIndividual(ringueIndividual.getGenero(), ringueIndividual.getFechado(),
                                                        ringueIndividual.getNumeroRingue(),
                                                        ringueIndividual.getNumeroRodada(),
                                                        ringueIndividual.getIdade(), ringueIndividual.getNivel(), null,
                                                        ringueIndividual.getTorneio(), null,
                                                        ringueIndividual.getRodadaJuiz());
            aux = ringueIndividualRepository.save(aux);
            if (ringueIndividual.getCompetidor() != null) {
                if (!ringueIndividual.getCompetidor().isEmpty()) {
                    aux.setCompetidor(ringueIndividual.getCompetidor());
                }
            }
            aux.setJuiz(ringueIndividual.getJuiz());
            aux.setCategoriaCompeticao(ringueIndividual.getCategoriaCompeticao());
            return ringueIndividualRepository.save(aux);
        }
        return ringueIndividualRepository.save(ringueIndividual);
    }

    private <T> T saveObj(Object obj) {
        Object o = null;
        if (RingueIndividual.class.equals(obj.getClass())) {
            o = this.save((RingueIndividual) obj);
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

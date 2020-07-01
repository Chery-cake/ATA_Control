package com.control.ata.repository.individual;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.tipo_pessoa.Competidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RingueIndividualRepository extends JpaRepository<RingueIndividual, Integer> {

    @Query("select c from Competidor c where c.ringueIndividualCollection = :ringue")
    Collection<Competidor> getCompetidoresByRingueIndividual (@Param("ringue")RingueIndividual ringue);

}

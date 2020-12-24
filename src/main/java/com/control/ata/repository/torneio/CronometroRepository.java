package com.control.ata.repository.torneio;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.torneio.Cronometro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CronometroRepository extends JpaRepository<Cronometro, Integer> {

    @Query("select c from Cronometro c where c.ringueIndividual = :ringueIndividual")
    Cronometro getByRingueIndividual(@Param("ringueIndividual") RingueIndividual ringueIndividual);

}

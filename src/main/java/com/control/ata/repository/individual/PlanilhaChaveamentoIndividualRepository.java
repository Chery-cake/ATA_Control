package com.control.ata.repository.individual;

import com.control.ata.model.individual.PlanilhaChaveamentoIndividual;
import com.control.ata.model.individual.RingueIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanilhaChaveamentoIndividualRepository extends JpaRepository<PlanilhaChaveamentoIndividual, Integer> {

    @Query("select p from PlanilhaChaveamentoIndividual p where p.ringueIndividual = :ringueIndividual")
    List<PlanilhaChaveamentoIndividual> getAllByRingueIndividual(@Param("ringueIndividual") RingueIndividual ringueIndividual);

}

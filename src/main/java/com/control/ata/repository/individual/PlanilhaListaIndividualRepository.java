package com.control.ata.repository.individual;

import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanilhaListaIndividualRepository extends JpaRepository<PlanilhaListaIndividual, Integer> {

    @Query("select p from PlanilhaListaIndividual p where p.ringueIndividual = :ringueIndividual")
    List<PlanilhaListaIndividual> getAllByRingueIndividual(@Param("ringueIndividual")RingueIndividual ringueIndividual);

}

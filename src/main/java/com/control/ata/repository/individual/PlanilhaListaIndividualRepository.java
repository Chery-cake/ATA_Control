package com.control.ata.repository.individual;

import com.control.ata.model.individual.PlanilhaListaIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanilhaListaIndividualRepository extends JpaRepository<PlanilhaListaIndividual, Integer> {
}

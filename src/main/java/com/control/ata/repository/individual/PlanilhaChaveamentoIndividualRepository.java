package com.control.ata.repository.individual;

import com.control.ata.model.individual.PlanilhaChaveamentoIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanilhaChaveamentoIndividualRepository extends JpaRepository<PlanilhaChaveamentoIndividual, Integer> {
}

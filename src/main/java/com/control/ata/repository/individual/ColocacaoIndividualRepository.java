package com.control.ata.repository.individual;

import com.control.ata.model.individual.ColocacaoIndividual;
import com.control.ata.model.individual.PlanilhaChaveamentoIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColocacaoIndividualRepository extends JpaRepository<ColocacaoIndividual, Integer> {

    @Query("select c from ColocacaoIndividual c where c.planilhaChaveamentoIndividual = :planilha")
    ColocacaoIndividual getColocacaoIndividualByPlanilhaChaveamentoIndividual(@Param("planilha")PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual);
}

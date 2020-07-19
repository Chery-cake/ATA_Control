package com.control.ata.repository.individual;

import com.control.ata.model.individual.ChaveListaIndividual;
import com.control.ata.model.individual.PlanilhaListaIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChaveListaIndividualRepository extends JpaRepository<ChaveListaIndividual, Integer> {

    @Query("select c from ChaveListaIndividual c where c.planilhaListaIndividual = :planilha")
    Collection<ChaveListaIndividual> getAllByPlanilhaChaveamentoIndividual(
            @Param("planilha") PlanilhaListaIndividual planilha);

}

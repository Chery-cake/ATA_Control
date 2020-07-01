package com.control.ata.repository.individual;

import com.control.ata.model.individual.PlanilhaListaIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanilhaListaIndividualRepository extends JpaRepository<PlanilhaListaIndividual, Integer> {

    @Query("update PlanilhaListaIndividual p set p = :planilha where p.id = :planilha")
    void updatePlanilha (@Param("planilha") PlanilhaListaIndividual planilha);

}

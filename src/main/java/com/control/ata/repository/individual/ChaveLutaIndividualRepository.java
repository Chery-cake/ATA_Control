package com.control.ata.repository.individual;

import com.control.ata.model.individual.ChaveLutaIndividual;
import com.control.ata.model.individual.PlanilhaChaveamentoIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChaveLutaIndividualRepository extends JpaRepository<ChaveLutaIndividual, Integer> {

    @Query("select c from ChaveLutaIndividual c where c.planilhaChaveamentoIndividual = :planilha")
    Collection<ChaveLutaIndividual> getAllByPlanilhaChaveamentoIndividual(@Param("planilha")
            PlanilhaChaveamentoIndividual planilha);

    @Query("select c from ChaveLutaIndividual c where c.planilhaChaveamentoIndividual = :planilha and c.fase = :fase")
    Collection<ChaveLutaIndividual> getAllByPlanilhaChaveamentoIndividualAndFase(@Param("planilha")
            PlanilhaChaveamentoIndividual planilha, @Param("fase") Integer fase);

}

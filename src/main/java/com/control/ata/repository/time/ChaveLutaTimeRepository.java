package com.control.ata.repository.time;

import com.control.ata.model.time.ChaveLutaTime;
import com.control.ata.model.time.PlanilhaChaveamentoTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChaveLutaTimeRepository extends JpaRepository<ChaveLutaTime, Integer> {

    @Query("select t from ChaveLutaTime t where t.planilhaChaveamentoTime = :planilha")
    Collection<ChaveLutaTime> getAllByPlanilhaChaveamentoTime(@Param("planilha") PlanilhaChaveamentoTime planilha);

    @Query("select t from ChaveLutaTime t where t.planilhaChaveamentoTime = :planilha and t.fase = :fase")
    Collection<ChaveLutaTime> getAllByPlanilhaChaveamentoTimeAndFase(
            @Param("planilha") PlanilhaChaveamentoTime planilha, @Param("fase") Integer fase);

}

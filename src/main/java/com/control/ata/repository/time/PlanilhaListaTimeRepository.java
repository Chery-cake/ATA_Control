package com.control.ata.repository.time;

import com.control.ata.model.time.PlanilhaListaTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanilhaListaTimeRepository extends JpaRepository<PlanilhaListaTime, Integer> {

    @Query("update PlanilhaListaTime p set p = :planilha where p.id = :planilha")
    void updatePlanilha (@Param("planilha") PlanilhaListaTime planilha);

}
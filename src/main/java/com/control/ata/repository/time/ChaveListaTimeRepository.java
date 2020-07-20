package com.control.ata.repository.time;

import com.control.ata.model.time.ChaveListaTime;
import com.control.ata.model.time.PlanilhaListaTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChaveListaTimeRepository extends JpaRepository<ChaveListaTime, Integer> {

    @Query("select t from ChaveListaTime t where t.planilhaListaTime = :planilha")
    Collection<ChaveListaTime> getAllByPlanilhaListaTime(@Param("planilha")PlanilhaListaTime planilha);

}

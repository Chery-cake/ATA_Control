package com.control.ata.repository.time;

import com.control.ata.model.time.PlanilhaChaveamentoTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanilhaChaveamentoTimeRepository extends JpaRepository<PlanilhaChaveamentoTime, Integer> {
}

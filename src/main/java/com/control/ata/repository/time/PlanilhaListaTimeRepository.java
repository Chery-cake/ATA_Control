package com.control.ata.repository.time;

import com.control.ata.model.time.PlanilhaListaTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanilhaListaTimeRepository extends JpaRepository<PlanilhaListaTime, Integer> {
}
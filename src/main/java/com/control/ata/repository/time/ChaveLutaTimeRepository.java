package com.control.ata.repository.time;

import com.control.ata.model.time.ChaveLutaTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChaveLutaTimeRepository extends JpaRepository<ChaveLutaTime, Integer> {
}

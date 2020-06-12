package com.control.ata.repository.torneio;

import com.control.ata.model.torneio.Cronometro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronometroRepository extends JpaRepository<Cronometro, Integer> {
}

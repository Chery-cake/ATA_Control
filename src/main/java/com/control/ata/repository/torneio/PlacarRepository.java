package com.control.ata.repository.torneio;

import com.control.ata.model.torneio.Placar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacarRepository extends JpaRepository<Placar, Integer> {
}

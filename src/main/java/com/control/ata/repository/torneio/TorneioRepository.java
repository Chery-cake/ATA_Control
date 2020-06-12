package com.control.ata.repository.torneio;

import com.control.ata.model.torneio.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorneioRepository extends JpaRepository<Torneio, Integer> {
}

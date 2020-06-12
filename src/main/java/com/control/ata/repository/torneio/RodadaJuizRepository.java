package com.control.ata.repository.torneio;

import com.control.ata.model.torneio.RodadaJuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RodadaJuizRepository extends JpaRepository<RodadaJuiz, Integer> {
}

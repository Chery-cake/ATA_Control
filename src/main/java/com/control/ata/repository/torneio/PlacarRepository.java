package com.control.ata.repository.torneio;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.torneio.Cronometro;
import com.control.ata.model.torneio.Placar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlacarRepository extends JpaRepository<Placar, Integer> {

}

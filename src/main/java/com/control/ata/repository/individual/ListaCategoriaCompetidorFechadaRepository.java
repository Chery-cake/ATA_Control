package com.control.ata.repository.individual;

import com.control.ata.model.individual.ListaCategoriaCompetidorFechada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaCategoriaCompetidorFechadaRepository extends JpaRepository<ListaCategoriaCompetidorFechada, Integer> {
}

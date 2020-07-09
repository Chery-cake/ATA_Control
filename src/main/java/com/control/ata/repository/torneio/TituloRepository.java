package com.control.ata.repository.torneio;

import com.control.ata.model.torneio.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Integer> {

}

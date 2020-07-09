package com.control.ata.repository.torneio;

import com.control.ata.model.torneio.CategoriaTitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaTituloRepository extends JpaRepository<CategoriaTitulo, Integer> {

}

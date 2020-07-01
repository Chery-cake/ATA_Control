package com.control.ata.repository.tipo_pessoa;

import com.control.ata.model.tipo_pessoa.Competidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetidorRepository extends JpaRepository<Competidor, Integer> {

}

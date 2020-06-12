package com.control.ata.repository.tipo_pessoa;

import com.control.ata.model.tipo_pessoa.Juiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuizRepository extends JpaRepository<Juiz, Integer> {
}

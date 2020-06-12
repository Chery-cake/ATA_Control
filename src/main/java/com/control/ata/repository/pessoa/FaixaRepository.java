package com.control.ata.repository.pessoa;

import com.control.ata.model.pessoa.Faixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaixaRepository extends JpaRepository<Faixa, Integer> {
}

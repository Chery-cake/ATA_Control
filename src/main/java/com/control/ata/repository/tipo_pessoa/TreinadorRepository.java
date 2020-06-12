package com.control.ata.repository.tipo_pessoa;

import com.control.ata.model.tipo_pessoa.Treinador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreinadorRepository extends JpaRepository<Treinador, Integer> {
}

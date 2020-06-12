package com.control.ata.repository.tipo_pessoa;

import com.control.ata.model.tipo_pessoa.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Integer> {
}

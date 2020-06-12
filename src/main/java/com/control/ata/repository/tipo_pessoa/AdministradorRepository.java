package com.control.ata.repository.tipo_pessoa;

import com.control.ata.model.tipo_pessoa.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
}

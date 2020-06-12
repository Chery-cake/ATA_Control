package com.control.ata.repository.endereco;

import com.control.ata.model.endereco.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Integer> {
}

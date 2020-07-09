package com.control.ata.repository.endereco;

import com.control.ata.model.endereco.Academia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademiaRepository extends JpaRepository<Academia, Integer> {
}

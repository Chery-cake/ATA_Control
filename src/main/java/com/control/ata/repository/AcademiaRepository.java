package com.control.ata.repository;

import com.control.ata.model.Academia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademiaRepository extends JpaRepository<Academia, Integer> {
}

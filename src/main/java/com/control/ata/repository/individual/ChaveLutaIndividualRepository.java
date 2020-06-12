package com.control.ata.repository.individual;

import com.control.ata.model.individual.ChaveLutaIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChaveLutaIndividualRepository extends JpaRepository<ChaveLutaIndividual, Integer> {
}

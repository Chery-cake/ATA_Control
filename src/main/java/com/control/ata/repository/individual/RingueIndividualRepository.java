package com.control.ata.repository.individual;

import com.control.ata.model.individual.RingueIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RingueIndividualRepository extends JpaRepository<RingueIndividual, Integer> {

}

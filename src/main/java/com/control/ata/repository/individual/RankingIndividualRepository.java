package com.control.ata.repository.individual;

import com.control.ata.model.individual.RankingIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingIndividualRepository extends JpaRepository<RankingIndividual, Integer> {
}

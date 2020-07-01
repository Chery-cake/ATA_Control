package com.control.ata.repository.individual;

import com.control.ata.model.individual.ChaveLutaIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChaveLutaIndividualRepository extends JpaRepository<ChaveLutaIndividual, Integer> {

    @Query("update ChaveLutaIndividual c set c = :chave where c.id = :chave")
    void updateChave(@Param("chave") ChaveLutaIndividual chave);

}

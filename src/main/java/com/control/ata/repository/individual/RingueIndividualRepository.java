package com.control.ata.repository.individual;

import com.control.ata.model.individual.RingueIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RingueIndividualRepository extends JpaRepository<RingueIndividual, Integer> {

    @Query("select r from RingueIndividual r where r.idade = :idade and r.nivel = :nivel")
    Collection<RingueIndividual> getAllByIdadeAndNivel(@Param("idade") String idade, @Param("nivel") String nivel);

}

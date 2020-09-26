package com.control.ata.repository.individual;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.torneio.RodadaJuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RingueIndividualRepository extends JpaRepository<RingueIndividual, Integer> {

    @Query("select r from RingueIndividual r where r.idade = :idade and r.nivel = :nivel")
    Collection<RingueIndividual> getAllByIdadeAndNivel(@Param("idade") String idade, @Param("nivel") Integer nivel);

    @Query("select r from RingueIndividual r where r.rodadaJuiz = :rodadaJuiz")
    List<RingueIndividual> getAllByRodadaJuiz(@Param("rodadaJuiz") RodadaJuiz rodadaJuiz);

}

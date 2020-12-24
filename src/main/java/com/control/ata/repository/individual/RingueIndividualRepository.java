package com.control.ata.repository.individual;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.model.torneio.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RingueIndividualRepository extends JpaRepository<RingueIndividual, Integer> {

    @Query("select r from RingueIndividual r where r.idade = :idade and r.nivel = :nivel and r.torneio = :torneio")
    Collection<RingueIndividual> getAllByIdadeAndNivelAndTorneio(@Param("idade") Integer idade,
            @Param("nivel") Integer nivel, @Param("torneio") Torneio torneio);

    @Query("select r from RingueIndividual r where r.rodadaJuiz = :rodadaJuiz")
    List<RingueIndividual> getAllByRodadaJuiz(@Param("rodadaJuiz") RodadaJuiz rodadaJuiz);

    @Query("select r from RingueIndividual r where r.torneio = :torneio")
    List<RingueIndividual> getAllByTorneio(@Param("torneio") Torneio torneio);

    @Query("select r from RingueIndividual r where r.numeroRingue = :numero and r.finalizado = :finalizado")
    List<RingueIndividual> getAllByNumeroRingueAndFinalizado(@Param("numero") Integer numero, @Param("finalizado") Boolean finalizado);

}

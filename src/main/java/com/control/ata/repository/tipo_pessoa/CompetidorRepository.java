package com.control.ata.repository.tipo_pessoa;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetidorRepository extends JpaRepository<Competidor, Integer> {

    @Query("select c from Competidor c where c.torneio = :torneio")
    List<Competidor> getAllByTorneio(@Param("torneio") Torneio torneio);

    @Query("select c from Competidor c join c.ringueIndividualCollection r where r.id = :id")
    List<Competidor> getAllByRingueIndividual(@Param("id")Integer id);

}
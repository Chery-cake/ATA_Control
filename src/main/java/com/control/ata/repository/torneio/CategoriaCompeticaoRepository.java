package com.control.ata.repository.torneio;

import com.control.ata.model.torneio.CategoriaCompeticao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaCompeticaoRepository extends JpaRepository<CategoriaCompeticao, Integer> {

    @Query("select c from CategoriaCompeticao c join c.competidorList C where C.id = :id")
    List<CategoriaCompeticao> getAllByCompetidor(@Param("id") Integer id);

    @Query("select c from CategoriaCompeticao c join c.ringueIndividualList r where r.id = :id")
    List<CategoriaCompeticao> getAllByRingueIndividual(@Param("id") Integer id);

    @Query("select c from CategoriaCompeticao c join c.rankingIndividualList r where r.id = :id")
    CategoriaCompeticao getAllByRankingIndividual(@Param("id") Integer id);

}
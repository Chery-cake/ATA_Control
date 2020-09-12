package com.control.ata.repository.torneio;

import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.model.torneio.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RodadaJuizRepository extends JpaRepository<RodadaJuiz, Integer> {

    @Query("select r from RodadaJuiz r where r.juiz = :juiz")
    RodadaJuiz getRodadaJuizByJuiz(@Param("juiz") Juiz juiz);

    @Query("select r from RodadaJuiz r where r.torneio = :torneio")
    List<RodadaJuiz> getAllByTorneio(@Param("torneio")Torneio torneio);

}

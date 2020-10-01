package com.control.ata.repository.torneio;

import com.control.ata.model.torneio.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TorneioRepository extends JpaRepository<Torneio, Integer> {

    @Query("select t from Torneio t where t.iniciado = :iniciado")
    List<Torneio> getAllByIniciado(@Param("iniciado") Boolean iniciado);

    @Query("select t from Torneio t where t.terminado = :terminado")
    List<Torneio> getAllByTerminado(@Param("terminado") Boolean terminado);

}

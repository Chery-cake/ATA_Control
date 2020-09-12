package com.control.ata.repository.tipo_pessoa;

import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuizRepository extends JpaRepository<Juiz, Integer> {

    @Query("select j from Juiz j where j.torneio = :torneio")
    List<Juiz> getAllByTorneio(@Param("torneio") Torneio torneio);

}

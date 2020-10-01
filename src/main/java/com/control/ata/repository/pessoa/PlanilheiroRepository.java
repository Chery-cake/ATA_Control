package com.control.ata.repository.pessoa;

import com.control.ata.model.pessoa.Planilheiro;
import com.control.ata.model.torneio.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanilheiroRepository extends JpaRepository<Planilheiro, Integer> {

    @Query("select p from Planilheiro p where p.torneio = :torneio")
    Planilheiro getByTorneio(@Param("torneio")Torneio torneio);

}

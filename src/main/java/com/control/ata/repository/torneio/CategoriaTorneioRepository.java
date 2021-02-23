package com.control.ata.repository.torneio;

import com.control.ata.model.torneio.CategoriaTorneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaTorneioRepository extends JpaRepository<CategoriaTorneio, Integer> {

    @Query("select c from CategoriaTorneio c where c.nome = :nome")
    CategoriaTorneio getCategoriaTorneioByNome(@Param("nome") String nome);

}

package com.control.ata.repository.pessoa;

import com.control.ata.model.pessoa.Faixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FaixaRepository extends JpaRepository<Faixa, Integer> {

    @Query("select f from Faixa f where f.nome = :nome")
    Faixa getFaixaByNome(@Param("nome") String nome);
}

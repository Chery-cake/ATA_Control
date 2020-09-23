package com.control.ata.repository.tipo_pessoa;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.tipo_pessoa.Juiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuizRepository extends JpaRepository<Juiz, Integer> {

    @Query("select j from Juiz j join j.rodadaJuizList r where r.id = :id")
    List<Juiz> getAllByRodadaJuizList(@Param("id") Integer id);

    @Query("select j from Juiz j where j.pessoa = :pessoa")
    Juiz getJuizByPessoa(@Param("pessoa")Pessoa pessoa);

}

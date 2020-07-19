package com.control.ata.repository.individual;

import com.control.ata.model.individual.RankingIndividual;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.torneio.CategoriaCompeticao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingIndividualRepository extends JpaRepository<RankingIndividual, Integer> {

    @Query("select r from RankingIndividual r where r.pessoa = :pessoa and r.categoriaCompeticao = :categoriaCompeticao")
    RankingIndividual getByPessoaAndCategoriaCompeticao(@Param("pessoa")Pessoa pessoa, @Param("categoriaCompeticao")
            CategoriaCompeticao categoriaCompeticao);

}

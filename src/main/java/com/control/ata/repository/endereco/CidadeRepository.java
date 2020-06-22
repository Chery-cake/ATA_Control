package com.control.ata.repository.endereco;

import com.control.ata.model.endereco.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Query("select c from Cidade c where c.nome = :cidade")
    Cidade getCidadeByNome(@Param("cidade") String cidade);

}
package com.control.ata.repository.tipo_pessoa;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.tipo_pessoa.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Integer> {

    @Query("select i from Instrutor i where i.pessoa = :pessoa")
    Instrutor findByPessoa(@Param("pessoa")Pessoa pessoa);

}

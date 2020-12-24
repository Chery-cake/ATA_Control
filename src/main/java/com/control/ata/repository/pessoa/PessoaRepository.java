package com.control.ata.repository.pessoa;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.tipo_pessoa.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query("select p from Pessoa p where p.instrutor = :instrutor and p.isInstrutor = :isInstrutor")
    List<Pessoa> getAllByInstrutorAndIsInstrutor(@Param("instrutor") Instrutor instrutor, @Param("isInstrutor")Boolean isInstrutor);

}

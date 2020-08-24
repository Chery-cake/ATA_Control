package com.control.ata.repository.pessoa;

import com.control.ata.model.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query("select p from Pessoa p where p.email = :email")
    Optional<Pessoa> findByEmail(@Param("email") String email);

}

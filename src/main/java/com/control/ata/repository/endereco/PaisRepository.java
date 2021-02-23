package com.control.ata.repository.endereco;

import com.control.ata.model.endereco.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {

    @Query("select p from Pais p where p.nome = :nome")
    Pais getPaisByNome(@Param("nome") String nome);
}

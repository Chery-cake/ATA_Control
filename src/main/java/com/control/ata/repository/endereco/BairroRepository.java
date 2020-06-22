package com.control.ata.repository.endereco;

import com.control.ata.model.endereco.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Integer> {

    @Query("select b from Bairro b where b.nome = :bairro")
    Bairro getBairroByNome(@Param("bairro") String bairro);

}

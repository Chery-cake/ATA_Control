package com.control.ata.repository.torneio;

import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.RodadaJuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RodadaJuizRepository extends JpaRepository<RodadaJuiz, Integer> {

//    @Query("select b from Bairro b where b.nome = :bairro")
//    Bairro getBairroByNome(@Param("bairro") String bairro);

    @Query("select r from RodadaJuiz r where r.juiz = :juiz")
    RodadaJuiz getRodadaJuizByJuiz(@Param("juiz") Juiz juiz);

}

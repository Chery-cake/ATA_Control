package com.control.ata.repository.individual;

import com.control.ata.model.individual.ListaCategoriaCompetidorFechada;
import com.control.ata.model.tipo_pessoa.Competidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaCategoriaCompetidorFechadaRepository extends JpaRepository<ListaCategoriaCompetidorFechada, Integer> {

    @Query("select lc from ListaCategoriaCompetidorFechada lc where lc.competidor = :competidor")
    ListaCategoriaCompetidorFechada getByCompetidor(@Param("competidor") Competidor competidor);

}

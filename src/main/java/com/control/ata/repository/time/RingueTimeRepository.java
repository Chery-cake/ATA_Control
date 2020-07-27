package com.control.ata.repository.time;

import com.control.ata.model.time.RingueTime;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RingueTimeRepository extends JpaRepository<RingueTime, Integer> {

    @Query("select r from RingueTime r where r.fechado = :fechado and r.torneio = :torneio and r.categoriaCompeticao = :categorias")
    Collection<RingueTime> getAllByFechadoAndTorneioAndCategoriaCompeticao(@Param("fechado") Boolean fechado, @Param("torneio")
            Torneio torneio, @Param("categorias")Collection<CategoriaCompeticao> categorias);

}

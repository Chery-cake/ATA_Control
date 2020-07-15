package com.control.ata.repository.torneio;

import com.control.ata.model.time.Time;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Integer> {

    @Query("select t from Titulo t where t.competidor = :competidor")
    Collection<Titulo> getAllByCompetidor(@Param("competidor")Competidor competidor);

    @Query("select t from Titulo t where t.competidor = :competidor and t.categoriaCompeticao = :categoriaCompeticao")
    Titulo getByCompetidorAndCategoriaCompeticao(@Param("competidor")Competidor competidor, @Param("categoriaCompeticao")
            CategoriaCompeticao categoriaCompeticao);

    @Query("select t from Titulo t where t.time = :time and t.categoriaCompeticao = :categoriaCompeticao")
    Titulo getByTimeAndCategoriaCompeticao(@Param("time")Time time, @Param("categoriaCompeticao")
            CategoriaCompeticao categoriaCompeticao);

}

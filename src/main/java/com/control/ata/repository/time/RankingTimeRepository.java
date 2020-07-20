package com.control.ata.repository.time;

import com.control.ata.model.time.RankingTime;
import com.control.ata.model.time.Time;
import com.control.ata.model.torneio.CategoriaCompeticao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingTimeRepository extends JpaRepository<RankingTime, Integer> {

    @Query("select r from RankingTime r where r.time = :time and r.categoriaCompeticao = :categoriaCompeticao")
    RankingTime getByTimeAndCategoriaCompeticao(@Param("time")Time time, @Param("categoriaCompeticao")
            CategoriaCompeticao categoriaCompeticao);

}

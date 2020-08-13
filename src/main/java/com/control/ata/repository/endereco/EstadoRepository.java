package com.control.ata.repository.endereco;

import com.control.ata.model.endereco.Estado;
import com.control.ata.model.endereco.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    @Query("select e from Estado e where e.pais = :pais")
    List<Estado> getAllByPais(@Param("pais") Pais pais);

}

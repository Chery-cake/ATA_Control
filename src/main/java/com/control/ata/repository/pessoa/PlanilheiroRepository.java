package com.control.ata.repository.pessoa;

import com.control.ata.model.pessoa.Planilheiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanilheiroRepository extends JpaRepository<Planilheiro, Integer> {
}

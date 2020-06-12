package com.control.ata.repository.time;

import com.control.ata.model.time.RankingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingTimeRepository extends JpaRepository<RankingTime, Integer> {
}

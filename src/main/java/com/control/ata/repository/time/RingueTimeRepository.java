package com.control.ata.repository.time;

import com.control.ata.model.time.RingueTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RingueTimeRepository extends JpaRepository<RingueTime, Integer> {
}

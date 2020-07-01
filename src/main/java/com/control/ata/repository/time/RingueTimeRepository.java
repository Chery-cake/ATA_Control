package com.control.ata.repository.time;

import com.control.ata.model.time.RingueTime;
import com.control.ata.model.time.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RingueTimeRepository extends JpaRepository<RingueTime, Integer> {

    @Query("select t from Time t where t.ringueTimeCollection = :ringue")
    Collection<Time> getCompetidoresByRingueTime (@Param("ringue") RingueTime ringue);

}

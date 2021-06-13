package com.control.ata.security.repository;

import com.control.ata.security.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

    @Query("select ct from ConfirmationToken ct where ct.confirmationToken = :token")
    Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(@Param("token") String token);

    @Modifying
    @Query("delete from ConfirmationToken tok where tok.id = :id")
    @Transactional
    void delete(Integer id);

}

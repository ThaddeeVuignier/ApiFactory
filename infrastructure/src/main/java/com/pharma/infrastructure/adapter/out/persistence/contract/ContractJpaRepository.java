package com.pharma.infrastructure.adapter.out.persistence.contract;

import com.pharma.infrastructure.adapter.out.persistence.contract.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface ContractJpaRepository extends JpaRepository<ContractEntity, UUID> {

    @Query("""
        SELECT c FROM ContractEntity c 
        WHERE c.clientId = :clientId 
        AND (c.endDate IS NULL OR c.endDate > :today)
        """)
    List<ContractEntity> findActiveByClientId(
            @Param("clientId") UUID clientId,
            @Param("today") LocalDate today
    );

    @Query("""
  SELECT c FROM ContractEntity c
  WHERE c.clientId = :clientId
    AND (c.endDate IS NULL OR c.endDate > :today)
    AND c.updatedAt > :updatedAfter
  """)
    List<ContractEntity> findActiveByClientIdUpdatedAfter(
            @Param("clientId") UUID clientId,
            @Param("today") LocalDate today,
            @Param("updatedAfter") OffsetDateTime updatedAfter
    );


    @Query("""
        SELECT COALESCE(SUM(c.costAmount), 0) 
        FROM ContractEntity c 
        WHERE c.clientId = :clientId 
        AND (c.endDate IS NULL OR c.endDate > :today)
        """)
    BigDecimal sumActiveCostByClientId(
            @Param("clientId") UUID clientId,
            @Param("today") LocalDate today
    );

    @Modifying
    @Query("""
        UPDATE ContractEntity c 
        SET c.endDate = :today 
        WHERE c.clientId = :clientId 
        AND (c.endDate IS NULL OR c.endDate > :today)
        """)
    void closeAllActiveForClient(
            @Param("clientId") UUID clientId,
            @Param("today") LocalDate today
    );
}
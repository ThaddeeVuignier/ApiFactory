package com.pharma.infrastructure.adapter.out.persistence.contract.entity;

import com.pharma.domain.contract.Contract;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "contracts", indexes = {
        @Index(name = "idx_contract_client_id", columnList = "client_id"),
        @Index(name = "idx_contract_end_date", columnList = "end_date"),
        @Index(name = "idx_contract_updated_at", columnList = "updated_at")
})
@Getter
@Setter
@NoArgsConstructor
public class ContractEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "cost_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal costAmount;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public Contract toDomain() {

        return Contract.reconstitute(
                id,
                clientId,
                startDate,
                endDate,
                costAmount,
                updatedAt
        );
    }

    public static ContractEntity fromDomain(Contract contract) {
        ContractEntity entity = new ContractEntity();
        entity.setId(contract.getId());
        entity.setClientId(contract.getClientId());
        entity.setStartDate(contract.getStartDate());
        entity.setEndDate(contract.getEndDate());
        entity.setCostAmount(contract.getCostAmount());
        entity.setUpdatedAt(contract.getUpdateDate());
        return entity;
    }
}
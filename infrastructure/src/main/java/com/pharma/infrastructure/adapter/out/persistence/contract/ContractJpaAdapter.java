package com.pharma.infrastructure.adapter.out.persistence.contract;

import com.pharma.domain.contract.Contract;
import com.pharma.domain.port.out.ContractRepository;
import com.pharma.infrastructure.adapter.out.persistence.contract.entity.ContractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ContractJpaAdapter implements ContractRepository {

    private final ContractJpaRepository jpaRepository;

    @Override
    public Contract save(Contract contract) {
        ContractEntity entity = ContractEntity.fromDomain(contract);
        ContractEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Contract> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(ContractEntity::toDomain);
    }

    @Override
    public List<Contract> findActiveByClientId(UUID clientId, LocalDate today) {
        return jpaRepository.findActiveByClientId(clientId, today)
                .stream()
                .map(ContractEntity::toDomain)
                .toList();
    }

    @Override
    public List<Contract> findByClientIdAndUpdatedAfter(UUID clientId, OffsetDateTime updatedAfter) {
        return jpaRepository.findByClientIdAndUpdatedAfter(clientId, updatedAfter)
                .stream()
                .map(ContractEntity::toDomain)
                .toList();
    }

    @Override
    public BigDecimal sumActiveCostByClientId(UUID clientId, LocalDate today) {
        return jpaRepository.sumActiveCostByClientId(clientId, today);
    }

    @Override
    public void closeAllForClient(UUID clientId, LocalDate today) {
        jpaRepository.closeAllActiveForClient(clientId, today);
    }
}
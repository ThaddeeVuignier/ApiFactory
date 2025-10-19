package com.pharma.domain.port.out;

import com.pharma.domain.contract.Contract;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository {

    Contract save(Contract contract);
    Optional<Contract> findById(UUID id);
    List<Contract> findActiveByClientId(UUID clientId, LocalDate today);
    List<Contract> findActiveByClientIdUpdatedAfter(UUID clientId, LocalDate today, OffsetDateTime updatedAfter);
    BigDecimal sumActiveCostByClientId(UUID clientId, LocalDate today);
    void closeAllForClient(UUID clientId, LocalDate today);
}

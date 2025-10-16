package com.pharma.infrastructure.adapter.in.web.contract.dto;

import com.pharma.domain.contract.Contract;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ContractResponse(
        UUID id,
        UUID clientId,
        LocalDate startDate,
        LocalDate endDate,  // Can be null
        BigDecimal costAmount
) {
    public static ContractResponse from(Contract contract) {
        return new ContractResponse(
                contract.getId(),
                contract.getClientId(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getCostAmount()
        );
    }
}
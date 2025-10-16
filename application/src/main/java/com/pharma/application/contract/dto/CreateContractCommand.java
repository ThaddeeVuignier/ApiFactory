package com.pharma.application.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateContractCommand(
        UUID clientId,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal costAmount
) {
    public CreateContractCommand {
        if (clientId == null) throw new IllegalArgumentException("clientId is required");
        if (costAmount == null || costAmount.signum() < 0)
            throw new IllegalArgumentException("costAmount must be >= 0");
        if (startDate != null && endDate != null && startDate.isAfter(endDate))
            throw new IllegalArgumentException("startDate must be before endDate");
    }
}

package com.pharma.application.contract.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateContractCostCommand(
        UUID contractId,
        BigDecimal newCostAmount
) {
    public UpdateContractCostCommand {
        if (contractId == null) throw new IllegalArgumentException("contractId is required");
        if (newCostAmount == null || newCostAmount.signum() < 0)
            throw new IllegalArgumentException("newCostAmount must be >= 0");
    }
}

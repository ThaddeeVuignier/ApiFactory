package com.pharma.infrastructure.adapter.in.web.contract.dto;

import com.pharma.application.contract.dto.UpdateContractCostCommand;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateCostRequest(
        @NotNull(message = "New cost amount is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Cost must be >= 0")
        BigDecimal newCostAmount
) {
    public UpdateContractCostCommand toCommand(UUID contractId) {
        return new UpdateContractCostCommand(contractId, newCostAmount);
    }
}
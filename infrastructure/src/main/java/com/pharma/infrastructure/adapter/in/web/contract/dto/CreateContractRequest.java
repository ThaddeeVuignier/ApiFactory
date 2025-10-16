package com.pharma.infrastructure.adapter.in.web.contract.dto;

import com.pharma.application.contract.dto.CreateContractCommand;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateContractRequest(
        @NotNull(message = "Client ID is required")
        UUID clientId,

        LocalDate startDate,

        LocalDate endDate,

        @NotNull(message = "Cost amount is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Cost must be >= 0")
        BigDecimal costAmount
) {
    public CreateContractCommand toCommand() {
        return new CreateContractCommand(clientId, startDate, endDate, costAmount);
    }
}
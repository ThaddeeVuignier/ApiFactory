package com.pharma.infrastructure.adapter.in.web.contract.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TotalCostResponse(
        UUID clientId,
        BigDecimal totalActiveCost
) {}
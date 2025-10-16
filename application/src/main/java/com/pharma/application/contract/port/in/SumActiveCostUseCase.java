package com.pharma.application.contract.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface SumActiveCostUseCase {
    BigDecimal sumActiveCost(UUID clientId);
}

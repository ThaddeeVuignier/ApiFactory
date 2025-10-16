package com.pharma.application.contract.port.in;

import com.pharma.application.contract.dto.UpdateContractCostCommand;
import com.pharma.domain.contract.Contract;

public interface UpdateContractCostUseCase {
    Contract updateCost(UpdateContractCostCommand command);
}

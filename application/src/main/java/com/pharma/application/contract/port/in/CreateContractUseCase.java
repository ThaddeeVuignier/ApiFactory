package com.pharma.application.contract.port.in;

import com.pharma.application.contract.dto.CreateContractCommand;
import com.pharma.domain.contract.Contract;

public interface CreateContractUseCase {
    Contract create(CreateContractCommand command);
}

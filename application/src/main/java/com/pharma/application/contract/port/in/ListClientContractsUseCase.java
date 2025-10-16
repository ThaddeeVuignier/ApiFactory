package com.pharma.application.contract.port.in;

import com.pharma.application.contract.dto.GetContractsQuery;
import com.pharma.domain.contract.Contract;

import java.util.List;

public interface ListClientContractsUseCase {
    List<Contract> listByClient(GetContractsQuery query);
}

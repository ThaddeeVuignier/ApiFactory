package com.pharma.application.contract.service;

import com.pharma.application.contract.dto.CreateContractCommand;
import com.pharma.application.contract.dto.GetContractsQuery;
import com.pharma.application.contract.dto.UpdateContractCostCommand;
import com.pharma.application.contract.port.in.CreateContractUseCase;
import com.pharma.application.contract.port.in.ListClientContractsUseCase;
import com.pharma.application.contract.port.in.SumActiveCostUseCase;
import com.pharma.application.contract.port.in.UpdateContractCostUseCase;
import com.pharma.application.exception.ClientNotFoundException;
import com.pharma.application.exception.ContractNotFoundException;
import com.pharma.domain.contract.Contract;
import com.pharma.domain.port.out.ClientRepository;
import com.pharma.domain.port.out.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ContractService implements
        CreateContractUseCase,
        ListClientContractsUseCase,
        UpdateContractCostUseCase,
        SumActiveCostUseCase {

    private final ContractRepository contractRepo;
    private final ClientRepository clientRepo;
    private final Clock clock;

    public ContractService(ContractRepository contractRepo,
                           ClientRepository clientRepo,
                           Clock clock) {
        this.contractRepo = contractRepo;
        this.clientRepo = clientRepo;
        this.clock = clock;
    }

    @Override
    public Contract create(CreateContractCommand cmd) {
        ensureClientExists(cmd.clientId());

        Contract contract = Contract.create(
                cmd.clientId(),
                cmd.startDate(),
                cmd.endDate(),
                cmd.costAmount(),
                clock
        );

        return contractRepo.save(contract);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contract> listByClient(GetContractsQuery query) {
        ensureClientExists(query.clientId());
        LocalDate today = LocalDate.now(clock);

        if (query.updatedAfter() != null) {
            return contractRepo.findActiveByClientIdUpdatedAfter(query.clientId(), today, query.updatedAfter());
        }
        return contractRepo.findActiveByClientId(query.clientId(), today);
    }


    @Override
    public Contract updateCost(UpdateContractCostCommand cmd) {
        Contract contract = contractRepo.findById(cmd.contractId())
                .orElseThrow(() -> new ContractNotFoundException(cmd.contractId()));

        contract.updateCost(cmd.newCostAmount(), clock);

        return contractRepo.save(contract);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal sumActiveCost(UUID clientId) {
        ensureClientExists(clientId);

        LocalDate today = LocalDate.now(clock);
        BigDecimal sum = contractRepo.sumActiveCostByClientId(clientId, today);

        return sum != null ? sum : BigDecimal.ZERO;
    }

    private void ensureClientExists(UUID clientId) {
        clientRepo.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
    }
}
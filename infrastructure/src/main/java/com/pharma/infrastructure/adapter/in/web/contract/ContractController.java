package com.pharma.infrastructure.adapter.in.web.contract;

import com.pharma.application.contract.dto.GetContractsQuery;
import com.pharma.application.contract.port.in.CreateContractUseCase;
import com.pharma.application.contract.port.in.ListClientContractsUseCase;
import com.pharma.application.contract.port.in.SumActiveCostUseCase;
import com.pharma.application.contract.port.in.UpdateContractCostUseCase;
import com.pharma.domain.contract.Contract;
import com.pharma.infrastructure.adapter.in.web.contract.dto.ContractResponse;
import com.pharma.infrastructure.adapter.in.web.contract.dto.CreateContractRequest;
import com.pharma.infrastructure.adapter.in.web.contract.dto.TotalCostResponse;
import com.pharma.infrastructure.adapter.in.web.contract.dto.UpdateCostRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients/{clientId}/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final CreateContractUseCase createContract;
    private final ListClientContractsUseCase listContracts;
    private final UpdateContractCostUseCase updateCost;
    private final SumActiveCostUseCase sumActiveCost;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse create(
            @PathVariable UUID clientId,
            @Valid @RequestBody CreateContractRequest request) {
        Contract contract = createContract.create(request.toCommand());
        return ContractResponse.from(contract);
    }

    @GetMapping
    public ResponseEntity<List<ContractResponse>> listActive(
            @PathVariable UUID clientId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime updatedAfter) {

        GetContractsQuery query = new GetContractsQuery(clientId, updatedAfter);
        List<Contract> contracts = listContracts.listByClient(query);

        List<ContractResponse> response = contracts.stream()
                .map(ContractResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/total-cost")
    public ResponseEntity<TotalCostResponse> getTotalCost(@PathVariable UUID clientId) {
        BigDecimal total = sumActiveCost.sumActiveCost(clientId);
        return ResponseEntity.ok(new TotalCostResponse(clientId, total));
    }

    @PatchMapping("/{contractId}/cost")
    public ResponseEntity<ContractResponse> updateCost(
            @PathVariable UUID clientId,
            @PathVariable UUID contractId,
            @Valid @RequestBody UpdateCostRequest request) {

        Contract contract = updateCost.updateCost(request.toCommand(contractId));
        return ResponseEntity.ok(ContractResponse.from(contract));
    }
}
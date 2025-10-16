package com.pharma.infrastructure.adapter.in.web.client;

import com.pharma.application.client.port.in.CreateClientUseCase;
import com.pharma.application.client.port.in.DeleteClientUseCase;
import com.pharma.application.client.port.in.GetClientUseCase;
import com.pharma.application.client.port.in.UpdateClientUseCase;
import com.pharma.domain.client.Client;
import com.pharma.domain.client.Company;
import com.pharma.domain.client.Person;
import com.pharma.infrastructure.adapter.in.web.client.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final CreateClientUseCase createClient;
    private final GetClientUseCase getClient;
    private final UpdateClientUseCase updateClient;
    private final DeleteClientUseCase deleteClient;

    @PostMapping("/persons")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse createPerson(@Valid @RequestBody CreatePersonRequest request) {
        Person person = createClient.createPerson(request.toCommand());
        return PersonResponse.from(person);
    }

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse createCompany(@Valid @RequestBody CreateCompanyRequest request) {
        Company company = createClient.createCompany(request.toCommand());
        return CompanyResponse.from(company);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable UUID id) {
        Client client = getClient.getById(id);
        return ResponseEntity.ok(ClientResponse.from(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateClientRequest request) {
        Client client = updateClient.update(id, request.toCommand());
        return ResponseEntity.ok(ClientResponse.from(client));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        deleteClient.delete(id);
    }
}
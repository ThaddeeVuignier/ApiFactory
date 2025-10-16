package com.pharma.application.client.service;

import com.pharma.application.client.dto.CreateCompanyCommand;
import com.pharma.application.client.dto.CreatePersonCommand;
import com.pharma.application.client.dto.UpdateClientCommand;
import com.pharma.application.client.port.in.*;
import com.pharma.application.exception.ClientNotFoundException;
import com.pharma.domain.client.*;
import com.pharma.domain.common.EmailAddress;
import com.pharma.domain.common.PhoneNumber;
import com.pharma.domain.port.out.ClientRepository;
import com.pharma.domain.port.out.ContractRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import java.time.Clock;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class ClientService implements
        CreateClientUseCase,
        GetClientUseCase,
        UpdateClientUseCase,
        DeleteClientUseCase {

    private final ClientRepository clientRepo;
    private final ContractRepository contractRepo;
    private final Clock clock;

    public ClientService(ClientRepository clientRepo, ContractRepository contractRepo, Clock clock) {
        this.clientRepo = clientRepo;
        this.contractRepo = contractRepo;
        this.clock = clock;
    }

    @Override
    public Person createPerson(CreatePersonCommand cmd) {
        Person person = new Person(
                null,
                cmd.name(),
                new EmailAddress(cmd.email()),
                new PhoneNumber(cmd.phone()),
                cmd.birthDate()
        );
        return (Person) clientRepo.save(person);
    }

    @Override
    public Company createCompany(CreateCompanyCommand cmd) {
        Company company = new Company(
                null,
                cmd.name(),
                new EmailAddress(cmd.email()),
                new PhoneNumber(cmd.phone()),
                cmd.companyIdentifier()
        );
        return (Company) clientRepo.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Client getById(UUID id) {
        return clientRepo.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public Client update(UUID id, UpdateClientCommand cmd) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        EmailAddress email = new EmailAddress(cmd.email());
        PhoneNumber phone = new PhoneNumber(cmd.phone());

        switch (client) {
            case Person p -> p.update(cmd.name(), email, phone);
            case Company c -> c.update(cmd.name(), email, phone);
        }

        return clientRepo.save(client);
    }

    @Override
    public void delete(UUID id) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        LocalDate today = LocalDate.now(clock);
        contractRepo.closeAllForClient(id, today);
        clientRepo.deleteById(id);
    }
}
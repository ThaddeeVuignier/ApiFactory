package com.pharma.domain.port.out;

import com.pharma.domain.client.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(UUID id);
    void deleteById(UUID id);
}

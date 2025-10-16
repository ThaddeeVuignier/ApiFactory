package com.pharma.application.client.port.in;

import com.pharma.domain.client.Client;

import java.util.UUID;

public interface GetClientUseCase {
    Client getById(UUID id);
}

package com.pharma.application.client.port.in;

import com.pharma.application.client.dto.UpdateClientCommand;
import com.pharma.domain.client.Client;

import java.util.UUID;

public interface UpdateClientUseCase {
    Client update(UUID id, UpdateClientCommand command);
}

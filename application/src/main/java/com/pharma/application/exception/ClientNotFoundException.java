package com.pharma.application.exception;

import java.util.UUID;

public final class ClientNotFoundException extends ApplicationException {
    public ClientNotFoundException(UUID id) {
        super("Client not found: " + id);
    }
    public ClientNotFoundException(String message) {
        super(message);
    }
}

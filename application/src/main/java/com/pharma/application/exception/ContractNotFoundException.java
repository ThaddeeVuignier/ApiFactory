package com.pharma.application.exception;

import java.util.UUID;

public final class ContractNotFoundException extends ApplicationException {
    public ContractNotFoundException(UUID id) {
        super("Contract not found: " + id);
    }
    public ContractNotFoundException(String message) {
        super(message);
    }
}

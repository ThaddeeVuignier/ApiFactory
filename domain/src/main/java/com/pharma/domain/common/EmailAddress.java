package com.pharma.domain.common;

import com.pharma.domain.exception.DomainValidationException;
import java.util.Locale;

public record EmailAddress(String value) {
    public EmailAddress(String value) {
        if (value == null) throw new DomainValidationException("email is required");
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if (!normalized.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw new DomainValidationException("invalid email");
        this.value = normalized;
    }

    @Override
    public String toString() {
        return value;
    }
}
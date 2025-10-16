package com.pharma.domain.common;

import com.pharma.domain.exception.DomainValidationException;

public record PhoneNumber(String value) {
    public PhoneNumber(String value) {
        if (value == null) throw new DomainValidationException("phone is required");
        String normalized = value.replaceAll("\\s+", "");
        if (!normalized.matches("^\\+?\\d{10,15}$"))
            throw new DomainValidationException("invalid phone");
        this.value = normalized;
    }

    @Override
    public String toString() {
        return value;
    }
}
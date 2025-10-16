package com.pharma.domain.client;

import com.pharma.domain.common.EmailAddress;
import com.pharma.domain.common.PhoneNumber;
import com.pharma.domain.exception.DomainValidationException;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public final class Company implements Client {

    private static final String COMPANY_ID_REGEX = "^[a-z]{3}-\\d{3}$";

    private final UUID id;
    private String name;
    private EmailAddress email;
    private PhoneNumber phone;
    private final String companyIdentifier;
    private final ClientType type = ClientType.COMPANY;

    public Company(UUID id,
                   String name,
                   EmailAddress email,
                   PhoneNumber phone,
                   String companyIdentifier) {

        this.id = (id == null) ? UUID.randomUUID() : id;
        this.name = validatedName(name);
        this.email = Objects.requireNonNull(email, "email is required");
        this.phone = Objects.requireNonNull(phone, "phone is required");
        this.companyIdentifier = normalizeAndValidateCompanyId(companyIdentifier);
    }

    public void update(String name, EmailAddress email, PhoneNumber phone) {
        this.name = validatedName(name);
        this.email = Objects.requireNonNull(email, "email is required");
        this.phone = Objects.requireNonNull(phone, "phone is required");
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public EmailAddress getEmail() { return email; }
    public PhoneNumber getPhone() { return phone; }
    public String getCompanyIdentifier() { return companyIdentifier; }
    public ClientType getType() { return type; }

    private static String validatedName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainValidationException("name must not be blank");
        }
        return name.trim();
    }

    private static String normalizeAndValidateCompanyId(String raw) {
        if (raw == null) {
            throw new DomainValidationException("companyIdentifier is required");
        }
        String normalized = raw.trim().toLowerCase(Locale.ROOT);
        if (!normalized.matches(COMPANY_ID_REGEX)) {
            throw new DomainValidationException("Invalid company identifier format (expected aaa-123)");
        }
        return normalized;
    }
}

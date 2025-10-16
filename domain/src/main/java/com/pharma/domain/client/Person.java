package com.pharma.domain.client;

import com.pharma.domain.common.EmailAddress;
import com.pharma.domain.common.PhoneNumber;
import com.pharma.domain.exception.DomainValidationException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public final class Person implements Client {

    private final UUID id;
    private String name;
    private EmailAddress email;
    private PhoneNumber phone;
    private final LocalDate birthDate;
    private final ClientType type = ClientType.PERSON;

    public Person(UUID id,
                  String name,
                  EmailAddress email,
                  PhoneNumber phone,
                  LocalDate birthDate) {

        this.id = (id == null) ? UUID.randomUUID() : id;
        this.name = validatedName(name);
        this.email = Objects.requireNonNull(email, "email is required");
        this.phone = Objects.requireNonNull(phone, "phone is required");
        this.birthDate = validateBirthDate(birthDate);
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
    public LocalDate getBirthDate() { return birthDate; }
    public ClientType getType() { return type; }

    private static String validatedName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainValidationException("name must not be blank");
        }
        return name.trim();
    }

    private static LocalDate validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new DomainValidationException("birthDate is required");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new DomainValidationException("birthDate must be in the past");
        }
        return birthDate;
    }
}

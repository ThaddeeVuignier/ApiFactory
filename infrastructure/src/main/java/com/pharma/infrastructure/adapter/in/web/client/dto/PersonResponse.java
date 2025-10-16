package com.pharma.infrastructure.adapter.in.web.client.dto;

import com.pharma.domain.client.Person;

import java.time.LocalDate;
import java.util.UUID;

public record PersonResponse(
        UUID id,
        String name,
        String email,
        String phone,
        LocalDate birthDate,
        String type
) implements ClientResponse {
    public static PersonResponse from(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getEmail().value(),
                person.getPhone().value(),
                person.getBirthDate(),
                "PERSON"
        );
    }
}
package com.pharma.application.client.dto;

import java.time.LocalDate;

public record CreatePersonCommand(
        String name,
        String email,
        String phone,
        LocalDate birthDate
) {}
package com.pharma.application.client.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreatePersonCommand(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^\\+?\\d{10,15}$") String phone,
        @NotNull @Past LocalDate birthDate
) {}

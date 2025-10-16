package com.pharma.application.client.dto;

import jakarta.validation.constraints.*;

/** Command used to update mutable fields of a client (name, email, phone). */
public record UpdateClientCommand(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^\\+?\\d{10,15}$") String phone
) {}

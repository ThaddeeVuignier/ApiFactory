package com.pharma.application.client.dto;

import jakarta.validation.constraints.*;

/** Command to create a Company client. */
public record CreateCompanyCommand(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^\\+?\\d{10,15}$") String phone,
        @NotBlank @Pattern(regexp = "^[a-z]{3}-\\d{3}$", flags = Pattern.Flag.CASE_INSENSITIVE)
        String companyIdentifier
) {}

package com.pharma.infrastructure.adapter.in.web.client.dto;

import com.pharma.application.client.dto.CreateCompanyCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCompanyRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Phone must be 10-15 digits, optionally starting with +")
        String phone,

        @NotBlank(message = "Company identifier is required")
        @Pattern(
                regexp = "^[a-zA-Z]{3}-\\d{3}$",
                message = "Company identifier must match format: aaa-123"
        )
        String companyIdentifier
) {
    public CreateCompanyCommand toCommand() {
        return new CreateCompanyCommand(name, email, phone, companyIdentifier);
    }
}
package com.pharma.infrastructure.adapter.in.web.client.dto;

import com.pharma.application.client.dto.UpdateClientCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateClientRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Phone must be 10-15 digits, optionally starting with +")
        String phone
) {
    public UpdateClientCommand toCommand() {
        return new UpdateClientCommand(name, email, phone);
    }
}
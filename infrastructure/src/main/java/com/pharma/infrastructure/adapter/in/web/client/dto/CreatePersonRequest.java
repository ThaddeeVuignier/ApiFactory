package com.pharma.infrastructure.adapter.in.web.client.dto;

import com.pharma.application.client.dto.CreatePersonCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CreatePersonRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Phone must be 10-15 digits, optionally starting with +")
        String phone,

        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate
) {
    public CreatePersonCommand toCommand() {
        return new CreatePersonCommand(name, email, phone, birthDate);
    }
}
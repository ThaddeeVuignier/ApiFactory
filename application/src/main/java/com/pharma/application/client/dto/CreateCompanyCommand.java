package com.pharma.application.client.dto;

public record CreateCompanyCommand(
        String name,
        String email,
        String phone,
        String companyIdentifier
) {}
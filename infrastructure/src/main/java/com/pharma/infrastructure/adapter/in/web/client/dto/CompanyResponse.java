package com.pharma.infrastructure.adapter.in.web.client.dto;

import com.pharma.domain.client.Company;

import java.util.UUID;

public record CompanyResponse(
        UUID id,
        String name,
        String email,
        String phone,
        String companyIdentifier,
        String type
) implements ClientResponse {
    public static CompanyResponse from(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getEmail().value(),
                company.getPhone().value(),
                company.getCompanyIdentifier(),
                "COMPANY"
        );
    }
}
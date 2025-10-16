package com.pharma.infrastructure.adapter.in.web.client.dto;

import com.pharma.domain.client.Client;
import com.pharma.domain.client.Company;
import com.pharma.domain.client.Person;

public sealed interface ClientResponse permits PersonResponse, CompanyResponse {

    static ClientResponse from(Client client) {
        return switch (client) {
            case Person p -> PersonResponse.from(p);
            case Company c -> CompanyResponse.from(c);
        };
    }
}
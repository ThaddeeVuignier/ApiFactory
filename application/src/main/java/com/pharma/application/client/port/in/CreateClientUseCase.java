package com.pharma.application.client.port.in;

import com.pharma.application.client.dto.CreateCompanyCommand;
import com.pharma.application.client.dto.CreatePersonCommand;
import com.pharma.domain.client.Company;
import com.pharma.domain.client.Person;

public interface CreateClientUseCase {

    Person createPerson(CreatePersonCommand command);

    Company createCompany(CreateCompanyCommand command);
}

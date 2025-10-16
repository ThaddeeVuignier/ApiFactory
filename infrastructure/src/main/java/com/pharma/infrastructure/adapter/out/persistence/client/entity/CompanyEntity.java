package com.pharma.infrastructure.adapter.out.persistence.client.entity;

import com.pharma.domain.client.Company;
import com.pharma.domain.common.EmailAddress;
import com.pharma.domain.common.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("COMPANY")
@Getter
@Setter
@NoArgsConstructor
public class CompanyEntity extends ClientEntity {

    @Column(name = "company_identifier", nullable = false, unique = true)
    private String companyIdentifier;

    @Override
    public Company toDomain() {
        return new Company(
                getId(),
                getName(),
                new EmailAddress(getEmail()),
                new PhoneNumber(getPhone()),
                companyIdentifier
        );
    }

    public static CompanyEntity fromDomain(Company company) {
        CompanyEntity entity = new CompanyEntity();
        entity.setId(company.getId());
        entity.setName(company.getName());
        entity.setEmail(company.getEmail().value());
        entity.setPhone(company.getPhone().value());
        entity.setCompanyIdentifier(company.getCompanyIdentifier());
        return entity;
    }
}
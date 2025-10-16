package com.pharma.infrastructure.adapter.out.persistence.client.entity;

import com.pharma.domain.client.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "clients")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "client_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class ClientEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    public abstract Client toDomain();

    public static ClientEntity fromDomain(Client client) {
        return switch (client) {
            case com.pharma.domain.client.Person p -> PersonEntity.fromDomain(p);
            case com.pharma.domain.client.Company c -> CompanyEntity.fromDomain(c);
        };
    }
}
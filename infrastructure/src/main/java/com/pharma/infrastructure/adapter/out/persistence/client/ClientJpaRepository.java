package com.pharma.infrastructure.adapter.out.persistence.client;

import com.pharma.infrastructure.adapter.out.persistence.client.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, UUID> {
}
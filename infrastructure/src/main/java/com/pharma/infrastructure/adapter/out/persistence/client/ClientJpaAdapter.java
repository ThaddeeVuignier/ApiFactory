package com.pharma.infrastructure.adapter.out.persistence.client;

import com.pharma.domain.client.Client;
import com.pharma.domain.port.out.ClientRepository;
import com.pharma.infrastructure.adapter.out.persistence.client.entity.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientJpaAdapter implements ClientRepository {

    private final ClientJpaRepository jpaRepository;

    @Override
    public Client save(Client client) {
        ClientEntity entity = ClientEntity.fromDomain(client);
        ClientEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(ClientEntity::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
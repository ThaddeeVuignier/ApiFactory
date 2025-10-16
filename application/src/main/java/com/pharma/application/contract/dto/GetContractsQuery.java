package com.pharma.application.contract.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record GetContractsQuery(
        UUID clientId,
        OffsetDateTime updatedAfter // peut être null si pas de filtre
) {
    public GetContractsQuery {
        if (clientId == null) throw new IllegalArgumentException("clientId is required");
    }
}

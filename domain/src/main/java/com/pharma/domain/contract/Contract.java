package com.pharma.domain.contract;

import com.pharma.domain.exception.DomainValidationException;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Contract {

    private final UUID id;
    private final UUID clientId;
    private final LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal costAmount;
    private OffsetDateTime updateDate;

    private Contract(UUID id,
                     UUID clientId,
                     LocalDate startDate,
                     LocalDate endDate,
                     BigDecimal costAmount,
                     OffsetDateTime updateDate) {

        this.id = id;
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.costAmount = costAmount;
        this.updateDate = updateDate;
    }

    public static Contract create(UUID clientId,
                                  LocalDate startDate,
                                  LocalDate endDate,
                                  BigDecimal costAmount,
                                  Clock clock) {
        Objects.requireNonNull(clock, "clock is required");
        if (clientId == null) throw new DomainValidationException("clientId is required");

        LocalDate today = LocalDate.now(clock);
        LocalDate effectiveStart = (startDate == null) ? today : startDate;
        if (endDate != null && endDate.isBefore(effectiveStart)) {
            throw new DomainValidationException("endDate must be >= startDate");
        }
        BigDecimal validated = validateAmount(costAmount);
        OffsetDateTime now = OffsetDateTime.now(clock);

        return new Contract(
                UUID.randomUUID(),
                clientId,
                effectiveStart,
                endDate,
                validated,
                now
        );
    }

    public void updateCost(BigDecimal newAmount, Clock clock) {
        Objects.requireNonNull(clock, "clock is required");
        this.costAmount = validateAmount(newAmount);
        touch(clock);
    }

    public void closeOn(LocalDate today, Clock clock) {
        Objects.requireNonNull(today, "today is required");
        Objects.requireNonNull(clock, "clock is required");
        if (today.isBefore(this.startDate)) {
            throw new DomainValidationException("endDate cannot be before startDate");
        }
        this.endDate = today;
        touch(clock);
    }

    public boolean isActive(LocalDate today) {
        Objects.requireNonNull(today, "today is required");
        return (endDate == null) || today.isBefore(endDate);
    }

    public boolean updatedAfter(OffsetDateTime threshold) {
        return threshold != null && updateDate.isAfter(threshold);
    }

    private static BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new DomainValidationException("cost must be >= 0");
        }
        return amount;
    }

    private void touch(Clock clock) {
        this.updateDate = OffsetDateTime.now(clock);
    }

    public UUID getId() { return id; }
    public UUID getClientId() { return clientId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public BigDecimal getCostAmount() { return costAmount; }
    /** Interne : l’API ne doit pas sérialiser ce champ. */
    public OffsetDateTime getUpdateDate() { return updateDate; }
}

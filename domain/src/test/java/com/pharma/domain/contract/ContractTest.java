package com.pharma.domain.contract;

import com.pharma.domain.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContractTest {

    private static final UUID CLIENT_ID = UUID.randomUUID();

    @Test
    void createUsesTodayWhenStartDateMissing() {
        Clock clock = Clock.fixed(Instant.parse("2024-01-15T10:00:00Z"), ZoneOffset.UTC);

        Contract contract = Contract.create(CLIENT_ID, null, null, new BigDecimal("123.45"), clock);

        assertThat(contract.getStartDate()).isEqualTo(LocalDate.of(2024, 1, 15));
        assertThat(contract.getEndDate()).isNull();
        assertThat(contract.getCostAmount()).isEqualByComparingTo("123.45");
        assertThat(contract.getUpdateDate()).isEqualTo(OffsetDateTime.ofInstant(clock.instant(), clock.getZone()));
        assertThat(contract.getClientId()).isEqualTo(CLIENT_ID);
        assertThat(contract.getId()).isNotNull();
    }

    @Test
    void createRejectsEndDateBeforeStartDate() {
        Clock clock = Clock.fixed(Instant.parse("2024-01-15T10:00:00Z"), ZoneOffset.UTC);

        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> Contract.create(CLIENT_ID,
                        LocalDate.of(2024, 1, 10),
                        LocalDate.of(2024, 1, 9),
                        BigDecimal.ONE,
                        clock)
        );

        assertThat(exception).hasMessage("endDate must be >= startDate");
    }

    @Test
    void createRequiresClientIdAndNonNegativeAmount() {
        Clock clock = Clock.fixed(Instant.parse("2024-01-15T10:00:00Z"), ZoneOffset.UTC);

        DomainValidationException missingClient = assertThrows(
                DomainValidationException.class,
                () -> Contract.create(null, LocalDate.now(), null, BigDecimal.ONE, clock)
        );
        assertThat(missingClient).hasMessage("clientId is required");

        DomainValidationException negativeAmount = assertThrows(
                DomainValidationException.class,
                () -> Contract.create(CLIENT_ID, LocalDate.now(), null, new BigDecimal("-1"), clock)
        );
        assertThat(negativeAmount).hasMessage("cost must be >= 0");
    }

    @Test
    void updateCostChangesAmountAndTimestamp() {
        Clock creationClock = Clock.fixed(Instant.parse("2024-01-01T08:00:00Z"), ZoneOffset.UTC);
        Contract contract = Contract.create(CLIENT_ID, LocalDate.of(2024, 1, 1), null, BigDecimal.TEN, creationClock);
        OffsetDateTime initialUpdateDate = contract.getUpdateDate();

        Clock updateClock = Clock.fixed(Instant.parse("2024-02-01T09:30:00Z"), ZoneOffset.UTC);
        contract.updateCost(new BigDecimal("15.00"), updateClock);

        assertThat(contract.getCostAmount()).isEqualByComparingTo("15.00");
        assertThat(contract.getUpdateDate()).isEqualTo(OffsetDateTime.ofInstant(updateClock.instant(), updateClock.getZone()));
        assertThat(contract.getUpdateDate()).isAfter(initialUpdateDate);
    }

    @Test
    void updateCostRejectsNegativeAmount() {
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T08:00:00Z"), ZoneOffset.UTC);
        Contract contract = Contract.create(CLIENT_ID, LocalDate.of(2024, 1, 1), null, BigDecimal.ONE, clock);

        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> contract.updateCost(new BigDecimal("-5"), clock)
        );

        assertThat(exception).hasMessage("cost must be >= 0");
    }

    @Test
    void closeOnSetsEndDateAndUpdatesTimestamp() {
        Clock creationClock = Clock.fixed(Instant.parse("2024-01-01T08:00:00Z"), ZoneOffset.UTC);
        Contract contract = Contract.create(CLIENT_ID, LocalDate.of(2024, 1, 1), null, BigDecimal.ONE, creationClock);

        Clock closeClock = Clock.fixed(Instant.parse("2024-01-10T12:00:00Z"), ZoneOffset.UTC);
        contract.closeOn(LocalDate.of(2024, 1, 10), closeClock);

        assertThat(contract.getEndDate()).isEqualTo(LocalDate.of(2024, 1, 10));
        assertThat(contract.getUpdateDate()).isEqualTo(OffsetDateTime.ofInstant(closeClock.instant(), closeClock.getZone()));
    }

    @Test
    void closeOnRejectsDateBeforeStart() {
        Clock clock = Clock.fixed(Instant.parse("2024-01-01T08:00:00Z"), ZoneOffset.UTC);
        Contract contract = Contract.create(CLIENT_ID, LocalDate.of(2024, 1, 1), null, BigDecimal.ONE, clock);

        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> contract.closeOn(LocalDate.of(2023, 12, 31), clock)
        );

        assertThat(exception).hasMessage("endDate cannot be before startDate");
    }

    @Test
    void isActiveReflectsEndDate() {
        Contract openContract = Contract.reconstitute(
                UUID.randomUUID(),
                CLIENT_ID,
                LocalDate.of(2024, 1, 1),
                null,
                BigDecimal.ONE,
                OffsetDateTime.now()
        );

        assertThat(openContract.isActive(LocalDate.of(2024, 1, 10))).isTrue();

        Contract closedContract = Contract.reconstitute(
                UUID.randomUUID(),
                CLIENT_ID,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 10),
                BigDecimal.ONE,
                OffsetDateTime.now()
        );

        assertThat(closedContract.isActive(LocalDate.of(2024, 1, 9))).isTrue();
        assertThat(closedContract.isActive(LocalDate.of(2024, 1, 10))).isFalse();
    }

    @Test
    void updatedAfterChecksTimestampWhenThresholdProvided() {
        OffsetDateTime updateDate = OffsetDateTime.parse("2024-01-15T10:00:00Z");
        Contract contract = Contract.reconstitute(
                UUID.randomUUID(),
                CLIENT_ID,
                LocalDate.of(2024, 1, 1),
                null,
                BigDecimal.ONE,
                updateDate
        );

        assertThat(contract.updatedAfter(null)).isFalse();
        assertThat(contract.updatedAfter(updateDate.minusMinutes(1))).isTrue();
        assertThat(contract.updatedAfter(updateDate.plusMinutes(1))).isFalse();
    }
}
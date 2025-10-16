package com.pharma.infrastructure.adapter.out.persistence.client.entity;

import com.pharma.domain.client.Person;
import com.pharma.domain.common.EmailAddress;
import com.pharma.domain.common.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("PERSON")
@Getter
@Setter
@NoArgsConstructor
public class PersonEntity extends ClientEntity {

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Override
    public Person toDomain() {
        return new Person(
                getId(),
                getName(),
                new EmailAddress(getEmail()),
                new PhoneNumber(getPhone()),
                birthDate
        );
    }

    public static PersonEntity fromDomain(Person person) {
        PersonEntity entity = new PersonEntity();
        entity.setId(person.getId());
        entity.setName(person.getName());
        entity.setEmail(person.getEmail().value());
        entity.setPhone(person.getPhone().value());
        entity.setBirthDate(person.getBirthDate());
        return entity;
    }
}
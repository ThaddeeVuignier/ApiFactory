package com.pharma.domain.client;

import com.pharma.domain.common.EmailAddress;
import com.pharma.domain.common.PhoneNumber;

import java.util.UUID;

public sealed interface Client permits Person, Company {
    UUID getId();
    String getName();
    EmailAddress getEmail();
    PhoneNumber getPhone();
    ClientType getType();
}

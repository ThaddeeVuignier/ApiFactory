package com.pharma.application.client.dto;


public record UpdateClientCommand(
        String name,
        String email,
        String phone
) {}
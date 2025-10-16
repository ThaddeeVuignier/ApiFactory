package com.pharma.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Clock;

@Configuration
@ComponentScan(basePackages = {
        "com.pharma.application",
        "com.pharma.infrastructure"
})
@EnableJpaRepositories(basePackages = "com.pharma.infrastructure.adapter.out.persistence")
@EntityScan(basePackages = "com.pharma.infrastructure.adapter.out.persistence")
public class ApplicationConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
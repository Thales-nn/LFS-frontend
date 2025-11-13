package com.locadorafilmes.locadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.locadorafilmes.locadora.model")
@EnableJpaRepositories("com.locadorafilmes.locadora.repository")
public class LocadoraApplication {
    public static void main(String[] args) {
        SpringApplication.run(LocadoraApplication.class, args);
    }
}
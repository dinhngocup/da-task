package com.petproject.datask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class DaTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaTaskApplication.class, args);
    }

}

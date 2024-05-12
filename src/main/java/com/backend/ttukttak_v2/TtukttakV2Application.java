package com.backend.ttukttak_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
public class TtukttakV2Application {

    public static void main(String[] args) {
        SpringApplication.run(TtukttakV2Application.class, args);
    }
}

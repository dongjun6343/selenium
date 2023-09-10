package com.work.selenium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SeleniumApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeleniumApplication.class, args);
    }

}

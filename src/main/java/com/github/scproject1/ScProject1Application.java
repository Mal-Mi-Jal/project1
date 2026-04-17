package com.github.scproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ScProject1Application {

    public static void main(String[] args) {
        SpringApplication.run(ScProject1Application.class, args);
    }

}

package com.example.scheduleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleServerApplication.class, args);
    }

}

package com.cskj.crawar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CrawarApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawarApplication.class, args);
    }

}

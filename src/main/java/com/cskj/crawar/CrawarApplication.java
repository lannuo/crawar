package com.cskj.crawar;

import com.mashape.unirest.http.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@EnableCaching
@SpringBootApplication
public class CrawarApplication {

    public static void main(String[] args) {
        Unirest.setConcurrency(100,10);
        Unirest.setTimeouts(4000,4000);
        SpringApplication.run(CrawarApplication.class, args);
    }

}

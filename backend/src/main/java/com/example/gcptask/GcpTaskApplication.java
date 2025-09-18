package com.example.gcptask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GcpTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(GcpTaskApplication.class, args);
    }
}
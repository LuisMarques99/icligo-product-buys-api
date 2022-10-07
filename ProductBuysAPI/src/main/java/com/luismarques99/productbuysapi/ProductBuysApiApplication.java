package com.luismarques99.productbuysapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProductBuysApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductBuysApiApplication.class, args);
    }

}

package com.andradscorporation.dscatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DscatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DscatalogApplication.class, args);
    }

}

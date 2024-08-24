package com.andradscorporation.dscatalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().info(new Info().title("DSCatalog API").version("v1").description("DsCatalog é uma API marketplace que o CEO da Andrad's Corporation usa para aprender e implementar conceitos de Rest e RestFull.").termsOfService("www.termsasandrads.com").license(new License().name("Apache 2.0").url("www.andradslicence.com")));
    }
}

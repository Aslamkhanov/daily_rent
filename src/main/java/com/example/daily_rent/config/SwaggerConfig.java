package com.example.daily_rent.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API для посуточной аренды квартир")
                        .description("Это API для управления квартирами, объявлениями, клиентами и бронированиями."));
    }
}

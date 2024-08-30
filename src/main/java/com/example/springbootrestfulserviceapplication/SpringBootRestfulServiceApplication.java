package com.example.springbootrestfulserviceapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class SpringBootRestfulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestfulServiceApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Отключаем CORS для всех маршрутов
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }

}

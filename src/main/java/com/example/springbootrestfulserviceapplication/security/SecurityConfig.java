package com.example.springbootrestfulserviceapplication.security;

import com.example.springbootrestfulserviceapplication.security.jwt.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Указывает, что данный класс содержит конфигурацию для Spring
@EnableWebSecurity // Включает поддержку веб-безопасности Spring Security
@EnableMethodSecurity // Включает поддержку безопасности на уровне методов
public class SecurityConfig {

    // Поля, содержащие зависимости для фильтра JWT и сервиса для работы с пользовательскими данными
    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;

    // Конструктор, принимающий необходимые зависимости
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    // Метод для настройки цепочки фильтров безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем защиту от CSRF (межсайтовой подделки запросов), так как она не нужна для REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register", "/auth/{userId:\\d+}").permitAll() // Разрешаем неавторизованный доступ к страницам авторизации и регистрации
                        .requestMatchers("/products/").permitAll() // Разрешаем неавторизованный доступ к странице с продуктами
                        .requestMatchers("/categories/").permitAll() // Разрешаем неавторизованный доступ к странице с продуктами
                        .requestMatchers("/products/productName").permitAll()
                        .anyRequest().authenticated() // Все остальные запросы требуют авторизации
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Добавляем фильтр JWT перед стандартным фильтром аутентификации

        return http.build(); // Возвращаем объект конфигурации безопасности
    }

    // Определяем бин для менеджера аутентификации, который используется для процесса аутентификации пользователей
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Определяем бин для кодировщика паролей, используемого для их шифрования в базе данных
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Используем BCrypt для шифрования паролей
    }
}

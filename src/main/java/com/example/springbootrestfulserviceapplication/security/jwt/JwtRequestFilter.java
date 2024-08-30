package com.example.springbootrestfulserviceapplication.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component // Указывает, что этот класс является компонентом Spring и может быть внедрен как зависимость
public class JwtRequestFilter extends OncePerRequestFilter {

    // Внедряем зависимость UserDetailsService для работы с данными пользователя
    private final UserDetailsService userDetailsService;

    // Внедряем зависимость JwtUtil для работы с JWT токенами
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil){
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    // Переопределяем метод doFilterInternal, который будет выполняться для каждого запроса
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Получаем заголовок Authorization из запроса
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Проверка наличия токена в заголовке и правильного формата
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Извлекаем токен, убирая префикс "Bearer "
            username = jwtUtil.extractUsername(jwt); // Извлекаем имя пользователя из токена
        }

        // Проверяем, был ли токен успешно извлечен и не установлена ли уже аутентификация
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Загружаем детали пользователя по имени пользователя
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Проверяем валидность токена (правильность и отсутствие истечения срока)
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {

                // Создаем объект для аутентификации на основе данных пользователя и его прав
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Устанавливаем детали аутентификации, такие как информация о запросе
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Устанавливаем аутентификацию в контексте безопасности Spring
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // Передаем управление следующему фильтру в цепочке
        chain.doFilter(request, response);
    }
}

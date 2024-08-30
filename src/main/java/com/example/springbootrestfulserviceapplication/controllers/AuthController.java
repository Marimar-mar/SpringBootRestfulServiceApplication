package com.example.springbootrestfulserviceapplication.controllers;

import com.example.springbootrestfulserviceapplication.models.Product;
import com.example.springbootrestfulserviceapplication.models.User;
import com.example.springbootrestfulserviceapplication.payload.response.ApiResponse;
import com.example.springbootrestfulserviceapplication.security.jwt.JwtUtil;
import com.example.springbootrestfulserviceapplication.payload.request.LoginUserRequest;
import com.example.springbootrestfulserviceapplication.payload.request.RegisterUserRequest;
import com.example.springbootrestfulserviceapplication.security.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin // Разрешает кросс-доменные запросы, чтобы клиент мог взаимодействовать с сервером из другого домена
@RestController // Аннотация указывает, что это контроллер Spring MVC, который будет обрабатывать HTTP-запросы
@RequestMapping("/auth") // Определяет базовый URL для всех методов этого контроллера
public class AuthController {

    // Менеджер аутентификации для выполнения процесса аутентификации
    private final AuthenticationManager authenticationManager;

    // Сервис для работы с пользователями
    private final UserService userService;

    // Утилита для работы с JWT (JSON Web Token)
    private final JwtUtil jwtUtil;

    // Сервис для загрузки данных о пользователе по имени пользователя
    private final UserDetailsService userDetailsService;

    // Конструктор для инициализации зависимостей
    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param request объект с данными для регистрации (имя пользователя, пароль и т.д.)
     * @return зарегистрированный пользователь
     */
    @PostMapping("/register")
    public User register(@RequestBody RegisterUserRequest request) {
        // Регистрируем нового пользователя через UserService
        User newUser = userService.registerUser(request);
        // Возвращаем зарегистрированного пользователя в качестве ответа
        return newUser;
    }

    /**
     * Аутентификация пользователя и генерация JWT токена.
     *
     * @param request объект с данными для аутентификации (email и пароль)
     * @return JWT токен для аутентифицированного пользователя
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginUserRequest request) {
        // Аутентификация пользователя с использованием предоставленных данных
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        // Загрузка деталей пользователя после успешной аутентификации
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        // Генерация JWT токена для аутентифицированного пользователя
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    /**
     * Поиск пользователя по ID.
     *
     * @param userId идентификатор пользователя, которого нужно найти
     * @return пользователь с указанным ID
     */
    @GetMapping(value = "/{userId:\\d+}")
    public ResponseEntity<User> FindUserById(@PathVariable int userId) {
        // Поиск пользователя по ID через UserService
        User user = userService.findById(userId);
        // Возвращаем пользователя в качестве ответа с HTTP статусом 200 OK
        return ResponseEntity.ok(user);
    }
}

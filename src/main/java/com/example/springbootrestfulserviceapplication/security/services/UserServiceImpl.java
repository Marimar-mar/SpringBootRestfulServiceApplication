package com.example.springbootrestfulserviceapplication.security.services;

import com.example.springbootrestfulserviceapplication.exceptions.UserCreationException;
import com.example.springbootrestfulserviceapplication.exceptions.UserNotFoundException;
import com.example.springbootrestfulserviceapplication.repository.UserRepository;
import com.example.springbootrestfulserviceapplication.models.User;
import com.example.springbootrestfulserviceapplication.payload.request.RegisterUserRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary // Указывает, что этот класс является основной реализацией интерфейса UserService
@Service // Аннотация указывает, что этот класс является сервисом Spring и может быть внедрен как зависимость
public class UserServiceImpl implements UserService {

    // Репозиторий для работы с пользователями в базе данных
    private final UserRepository userRepository;

    // Утилита для кодирования паролей
    private final PasswordEncoder passwordEncoder;

    // Конструктор для инициализации зависимостей
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param request объект с данными для регистрации (имя пользователя, email и пароль)
     * @return зарегистрированный пользователь
     */
    @Override
    public User registerUser(RegisterUserRequest request) {
        // Создаем нового пользователя с ролью "ROLE_USER" и закодированным паролем
        User user = new User(0, request.name(), request.email(), "ROLE_USER", passwordEncoder.encode(request.password()));
        // Сохраняем пользователя в базе данных через UserRepository
        return userRepository.save(user)
                // Если сохранение не удалось, выбрасываем исключение UserCreationException
                .orElseThrow(() -> new UserCreationException("Failed to create User in the database."));
    }

    /**
     * Поиск пользователя по email.
     *
     * @param UserEmail email пользователя, которого нужно найти
     * @return пользователь с указанным email
     */
    @Override
    public User findByEmail(String UserEmail) {
        // Ищем пользователя по email через UserRepository
        return userRepository.findByEmail(UserEmail)
                // Если пользователь не найден, выбрасываем исключение UserNotFoundException
                .orElseThrow(() -> new UserNotFoundException(UserEmail));
    }

    /**
     * Поиск пользователя по имени.
     *
     * @param UserName имя пользователя, которого нужно найти
     * @return пользователь с указанным именем
     */
    @Override
    public User findByName(String UserName) {
        // Ищем пользователя по имени через UserRepository
        return userRepository.findByName(UserName)
                // Если пользователь не найден, выбрасываем исключение UserNotFoundException
                .orElseThrow(() -> new UserNotFoundException(UserName));
    }

    /**
     * Поиск пользователя по ID.
     *
     * @param UserId идентификатор пользователя, которого нужно найти
     * @return пользователь с указанным ID
     */
    @Override
    public User findById(int UserId) {
        // Ищем пользователя по ID через UserRepository
        return userRepository.findById(UserId)
                // Если пользователь не найден, выбрасываем исключение UserNotFoundException
                .orElseThrow(() -> new UserNotFoundException(Integer.toString(UserId)));
    }
}

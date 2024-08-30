package com.example.springbootrestfulserviceapplication.security.services;

import com.example.springbootrestfulserviceapplication.exceptions.UserNotFoundException;
import com.example.springbootrestfulserviceapplication.repository.UserRepository;
import com.example.springbootrestfulserviceapplication.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service // Указывает, что этот класс является сервисом Spring и может быть внедрен как зависимость
public class MyUserDetailsService implements UserDetailsService {

    // Репозиторий для работы с пользователями в базе данных
    private final UserRepository userRepository;

    // Конструктор для внедрения зависимости UserRepository
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Метод для загрузки пользователя по email, необходим для аутентификации
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Ищем пользователя в базе данных по email, если не найден - выбрасываем исключение UserNotFoundException
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        List<GrantedAuthority> authorities = new ArrayList<>();
        String role = user.role(); // user.role() возвращает строку с ролью, например "ROLE_ADMIN"
        authorities.add(new SimpleGrantedAuthority(role));

        // Добавляем соответствующие остальные роли админу
        if ("ROLE_ADMIN".equals(role)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // Возвращаем объект UserDetails, который используется Spring Security для аутентификации
        return new org.springframework.security.core.userdetails.User(
                user.email(), // Email пользователя как имя пользователя
                user.password_hash(), // Захешированный пароль пользователя
                authorities // Список прав доступа пользователя
        );
    }
}

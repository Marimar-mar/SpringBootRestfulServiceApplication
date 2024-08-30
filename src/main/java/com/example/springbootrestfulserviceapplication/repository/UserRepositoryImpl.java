package com.example.springbootrestfulserviceapplication.repository;

import com.example.springbootrestfulserviceapplication.models.User;
import com.example.springbootrestfulserviceapplication.security.services.UserMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Optional;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository // Указывает, что этот класс является компонентом репозитория и взаимодействует с базой данных
public class UserRepositoryImpl implements UserRepository {

    // SQL-запрос для получения пользователя по имени
    private static final String SQL_GET_USER_BY_NAME =
            "select id, name, email, role, password_hash from users where name = :UserName";

    // SQL-запрос для получения пользователя по email
    private static final String SQL_GET_USER_BY_EMAIL =
            "select id, name, email, role, password_hash from users where email = :UserEmail";

    // SQL-запрос для получения пользователя по ID
    private static final String SQL_GET_USER_BY_ID =
            "select id, name, email, role, password_hash from users where id = :UserId";

    // SQL-запрос для вставки нового пользователя в базу данных
    private static final String SQL_INSERT_USER =
            "INSERT INTO users (name, email, role, password_hash) VALUES (:name, :email, :role, :password_hash)";

    // Объект UserMapper для преобразования результатов запроса в объекты User
    private final UserMapper userMapper;

    // Шаблон для выполнения SQL-запросов с именованными параметрами
    private final NamedParameterJdbcTemplate jdbcTemplate;

    // Конструктор для инициализации зависимостей
    public UserRepositoryImpl(UserMapper userMapper, NamedParameterJdbcTemplate jdbcTemplate) {
        this.userMapper = userMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Поиск пользователя по email.
     *
     * @param UserEmail email пользователя
     * @return Optional<User> найденный пользователь, если он есть
     */
    @Override
    public Optional<User> findByEmail(String UserEmail) {
        var params = new MapSqlParameterSource();
        params.addValue("UserEmail", UserEmail);
        // Выполняем запрос и возвращаем первый результат, если таковой имеется
        return jdbcTemplate.query(SQL_GET_USER_BY_EMAIL, params, userMapper).stream().findFirst();
    }

    /**
     * Поиск пользователя по имени.
     *
     * @param UserName имя пользователя
     * @return Optional<User> найденный пользователь, если он есть
     */
    @Override
    public Optional<User> findByName(String UserName) {
        var params = new MapSqlParameterSource();
        params.addValue("UserName", UserName);
        // Выполняем запрос и возвращаем первый результат, если таковой имеется
        return jdbcTemplate.query(SQL_GET_USER_BY_NAME, params, userMapper).stream().findFirst();
    }

    /**
     * Поиск пользователя по ID.
     *
     * @param UserId идентификатор пользователя
     * @return Optional<User> найденный пользователь, если он есть
     */
    @Override
    public Optional<User> findById(int UserId) {
        var params = new MapSqlParameterSource();
        params.addValue("UserId", UserId);
        // Выполняем запрос и возвращаем первый результат, если таковой имеется
        return jdbcTemplate.query(SQL_GET_USER_BY_ID, params, userMapper).stream().findFirst();
    }

    /**
     * Сохранение нового пользователя в базе данных.
     *
     * @param user объект пользователя для сохранения
     * @return Optional<User> сохраненный пользователь с установленным ID, если сохранение прошло успешно
     */
    @Override
    public Optional<User> save(User user) {
        // Создаем MapSqlParameterSource с параметрами для SQL-запроса
        var params = createSqlParameterSourceUser(user);

        // Создаем KeyHolder для получения сгенерированного ID
        KeyHolder NewUserKeyHolder = new GeneratedKeyHolder();

        // Выполняем SQL-запрос для вставки нового пользователя и сохраняем сгенерированный ID
        int rowsAffected = jdbcTemplate.update(SQL_INSERT_USER, params, NewUserKeyHolder, new String[]{"id"});

        if (rowsAffected > 0) {
            // Получаем сгенерированный ID
            Number generatedId = NewUserKeyHolder.getKey();
            if (generatedId != null) {
                // Возвращаем объект пользователя с установленным ID
                return findById(generatedId.intValue());
            }
        }
        // Возвращаем пустой Optional, если пользователь не был создан
        return Optional.empty();
    }

    /**
     * Приватный метод для создания MapSqlParameterSource из объекта User.
     *
     * @param user объект пользователя
     * @return MapSqlParameterSource параметры для SQL-запроса
     */
    private MapSqlParameterSource createSqlParameterSourceUser(User user) {
        var params = new MapSqlParameterSource();
        params.addValue("name", user.name());
        params.addValue("email", user.email());
        params.addValue("role", user.role());
        params.addValue("password_hash", user.password_hash());
        return params;
    }
}

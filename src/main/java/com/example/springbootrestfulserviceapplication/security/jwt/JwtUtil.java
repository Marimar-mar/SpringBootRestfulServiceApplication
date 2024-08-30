package com.example.springbootrestfulserviceapplication.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component // Указывает, что этот класс является компонентом Spring и может быть внедрен как зависимость
public class JwtUtil {

    // Генерация секретного ключа для подписи JWT на основе строки
    private final SecretKey secretKey = Keys.hmacShaKeyFor("q3zvB9J6eA7I0J2H9Lr7Sx8mKj5VbGh2YmJm9Pq2z8I=".getBytes());

    // Извлекает имя пользователя из токена JWT
    public String extractUsername(String token) {
        String userName = extractClaim(token, Claims::getSubject); // Получаем subject (имя пользователя) из токена
        return userName;
    }

    // Извлекает дату истечения срока действия токена JWT
    public Date extractExpiration(String token) {
        Date date = extractClaim(token, Claims::getExpiration); // Получаем дату истечения из токена
        return date;
    }

    // Универсальный метод для извлечения различных данных (claims) из токена JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Извлекаем все claims из токена
        return claimsResolver.apply(claims); // Применяем функцию для получения нужного значения
    }

    // Извлекает все claims из токена JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parser() // Создаем парсер JWT
                .verifyWith(secretKey) // Указываем секретный ключ для проверки подписи
                .build() // Строим объект парсера
                .parseSignedClaims(token) // Парсим токен и получаем claims
                .getPayload(); // Возвращаем полезную нагрузку (claims)
    }

    // Проверяет, истек ли срок действия токена
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Сравниваем дату истечения с текущей датой
    }

    // Генерирует новый токен JWT для указанного пользователя
    public String generateToken(String username) {
        return createToken(username); // Создаем токен с заданным именем пользователя
    }

    // Создает токен JWT с указанием subject (имени пользователя), времени выпуска и срока действия
    private String createToken(String subject) {
        return Jwts.builder() // Начинаем строить токен
                .subject(subject) // Устанавливаем имя пользователя как subject
                .issuedAt(new Date(System.currentTimeMillis())) // Указываем текущую дату как время выпуска токена
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Устанавливаем срок действия токена (10 часов)
                .signWith(secretKey) // Подписываем токен секретным ключом
                .compact(); // Компактно представляем токен в виде строки
    }

    // Проверяет, действителен ли токен: соответствует ли имя пользователя и не истек ли срок действия
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token); // Извлекаем имя пользователя из токена
        return (extractedUsername.equals(username) && !isTokenExpired(token)); // Проверяем совпадение имен и срок действия токена
    }
}

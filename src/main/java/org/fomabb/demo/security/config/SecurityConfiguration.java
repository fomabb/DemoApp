package org.fomabb.demo.security.config;

import lombok.RequiredArgsConstructor;
import org.fomabb.demo.security.service.UserServiceSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Конфигурация безопасности приложения.
 * Настраивает JWT аутентификацию, CORS и управление доступом к ресурсам.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserServiceSecurity userService;
}

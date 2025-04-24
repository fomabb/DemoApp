package org.fomabb.demo.security.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.security.dto.request.SignUpRequest;
import org.fomabb.demo.security.dto.response.JwtAuthenticationResponse;
import org.fomabb.demo.security.service.AuthenticationService;
import org.springframework.stereotype.Component;

/**
 * Фасад для аутентификации пользователей.
 * Обрабатывает логику регистрации пользователя, включая проверку пароля.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthenticationService authenticationService;

    /**
     * Обрабатывает запрос на регистрацию пользователя.
     * Проверяет, совпадает ли пароль с его подтверждением.
     *
     * @param request объект {@link SignUpRequest}, содержащий данные для регистрации
     * @return объект {@link JwtAuthenticationResponse}, содержащий информацию о JWT аутентификации
     * @throws BusinessException если пароли не совпадают
     */
    public JwtAuthenticationResponse signUpFacade(SignUpRequest request) {
        log.info("Начало проверки подтверждения пароля пользователя");
        if (request.getPassword().equals(request.getConfirmPassword())) {
            log.info("Пароль пользователя подтвержден успешно");
            return authenticationService.signUp(request);
        } else {
            log.warn("Пароль пользователя не совпадает с введенным изначально");
            throw new BusinessException("Пароль не совпадает с заданным Вами паролем");
        }
    }
}

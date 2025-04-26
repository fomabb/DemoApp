package org.fomabb.demo.security.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.demo.entity.Account;
import org.fomabb.demo.entity.EmailData;
import org.fomabb.demo.entity.PhoneData;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.security.dto.request.SignInRequest;
import org.fomabb.demo.security.dto.request.SignUpRequest;
import org.fomabb.demo.security.dto.response.JwtAuthenticationResponse;
import org.fomabb.demo.security.enumeration.Role;
import org.fomabb.demo.service.AccountService;
import org.fomabb.demo.service.EmailDataService;
import org.fomabb.demo.service.PhoneDataService;
import org.fomabb.demo.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {

    private final UserServiceSecurity userServiceSecurity;
    private final JwtServiceSecurity jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmailDataService emailDataService;
    private final PhoneDataService phoneDataService;
    private final AccountService accountService;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        log.info("Начато выполнение сборки и сохранения пользователя в базу данных");
        var user = User.builder()
                .name(request.getName())
                .dateOfBirth(request.getDateOfBirth())
                .primaryEmail(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .emailData(new HashSet<>())
                .phoneData(new HashSet<>())
                .build();
        userServiceSecurity.create(user);
        log.info("Пользователь сохранен в базу данных");
        accountService.createAccountWithBalance(Account.builder()
                .user(user)
                .balance(request.getBalance())
                .actualBalance(request.getBalance())
                .build());
        phoneDataService.phoneDataSave(PhoneData.builder()
                .user(user)
                .phone(request.getPhone())
                .build());
        emailDataService.emailDataSave(EmailData.builder()
                .user(user)
                .email(request.getEmail())
                .build());
        log.info("Email адрес добавлен в базу данных");
        log.info("Начало генерации токена для пользователя");
        var jwt = jwtService.generateToken(user);
        if (jwt.isEmpty()) {
            log.warn("Не удалось сгенерировать токен для пользователя");
            throw new BusinessException("Токен для пользователя не сгенерирован");
        } else {
            log.info("Токен для пользователя сгенерирован");
            return new JwtAuthenticationResponse(jwt);
        }
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Transactional
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        log.info("Попытка авторизации пользователя");

        User user = userService.findUserByEmail(request.getEmail());

        if (user == null) {
            throw new EntityNotFoundException("User with %s not found"
                    .formatted(request.getEmail()));
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getPrimaryEmail(),
                request.getPassword()
        ));

        var jwt = jwtService.generateToken(user);
        log.info("Пользователь успешно авторизован");
        return new JwtAuthenticationResponse(jwt);
    }
}

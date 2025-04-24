package org.fomabb.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.demo.entity.EmailData;
import org.fomabb.demo.entity.PhoneData;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.repository.UserRepository;
import org.fomabb.demo.security.service.UserServiceSecurity;
import org.fomabb.demo.service.EmailDataService;
import org.fomabb.demo.service.PhoneDataService;
import org.fomabb.demo.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailDataService emailDataService;
    private final PhoneDataService phoneDataService;
    private final UserServiceSecurity userServiceSecurity;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByPrimaryEmail(email)
                .orElseGet(() -> userRepository.findByEmailDataEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException("User with email %s not found"
                                .formatted(email))));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found"
                        .formatted(id)));
    }

    @Override
    @Transactional
    public void addEmailToUser(Long userId, String email) {
        User existingUser = findUserById(userId);
        Long validUserId = userServiceSecurity.getCurrentUser().getId();
        if (Objects.equals(existingUser.getId(), validUserId)) {
            emailDataService.emailDataSave(EmailData.builder()
                    .user(existingUser)
                    .email(email)
                    .build());
            log.info("Email адрес {} добавлен пользователю {}", email, existingUser.getName());
        } else {
            throw new AccessDeniedException("User does not have permission to update this task");
        }
    }

    @Override
    @Transactional
    public void addPhoneToUser(Long userId, String phone) {
        User existingUser = findUserById(userId);
        Long validUserId = userServiceSecurity.getCurrentUser().getId();
        if (Objects.equals(existingUser.getId(), validUserId)) {
            phoneDataService.phoneDataSave(PhoneData.builder()
                    .user(existingUser)
                    .phone(phone)
                    .build());
            log.info("Телефон {} добавлен пользователю {}", phone, existingUser.getName());
        } else {
            throw new AccessDeniedException("User does not have permission to update this task");
        }
    }
}

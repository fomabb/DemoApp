package org.fomabb.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.repository.UserRepository;
import org.fomabb.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByPrimaryEmail(email)
                .orElseGet(() -> userRepository.findByEmailDataEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException("User with email %s not found"
                                .formatted(email))));
    }
}

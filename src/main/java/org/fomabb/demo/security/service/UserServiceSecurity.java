package org.fomabb.demo.security.service;

import lombok.RequiredArgsConstructor;
import org.fomabb.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceSecurity {

    private final UserRepository userRepository;
}

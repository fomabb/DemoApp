package org.fomabb.demo.security.service;

import lombok.RequiredArgsConstructor;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.repository.UserRepository;
import org.fomabb.demo.security.enumeration.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceSecurity {

    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (user.getRole().equals(Role.ROLE_USER) && repository.existsByPrimaryEmail(user.getUsername())) {
            throw new BusinessException("The user with this email already exists");
        }

        if (user.getRole().equals(Role.ROLE_ADMIN) && repository.existsByPrimaryEmail(user.getUsername())) {
            throw new BusinessException("An employee with this username already exists");
        }
        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByPrimaryEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with not found"));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        var primaryEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(primaryEmail);
    }
}

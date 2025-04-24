package org.fomabb.demo.security.service;

import lombok.RequiredArgsConstructor;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.repository.UserRepository;
import org.fomabb.demo.security.enumeration.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
     * Извлечение ID пользователя из security context
     *
     * @return userId
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return ((User) userDetails).getId();
        }
        throw new BusinessException("Authentication principal is not of type UserDetails");
    }

    public Role getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return ((User) userDetails).getRole();
        }
        throw new BusinessException("Authentication principal is not of type UserDetails");
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

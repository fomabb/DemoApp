package org.fomabb.demo.service;

import org.fomabb.demo.entity.User;

public interface UserService {

    User findUserByEmail(String email);

    User findUserById(Long id);

    void addEmailToUser(Long userId, String email);
}

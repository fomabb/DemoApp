package org.fomabb.demo.service;

import org.fomabb.demo.entity.User;

public interface UserService {

    User findUserByEmail(String email);
}

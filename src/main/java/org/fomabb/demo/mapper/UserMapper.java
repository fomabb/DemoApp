package org.fomabb.demo.mapper;

import org.fomabb.demo.dto.response.UserdataDtoResponse;
import org.fomabb.demo.entity.User;

import java.util.List;

public interface UserMapper {

    List<UserdataDtoResponse> listEntityUserToListUserDto(List<User> users);
}

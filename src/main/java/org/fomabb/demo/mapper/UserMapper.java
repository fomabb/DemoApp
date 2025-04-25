package org.fomabb.demo.mapper;

import org.fomabb.demo.dto.UserDataDto;
import org.fomabb.demo.dto.response.UserdataDtoResponse;
import org.fomabb.demo.entity.User;

import java.util.List;

public interface UserMapper {

    UserDataDto entityToUserDataDto(User user);

    List<UserdataDtoResponse> listEntityUserToListUserDto(List<User> users);

    List<UserDataDto> listEntityToUserListDto(List<User> content);
}
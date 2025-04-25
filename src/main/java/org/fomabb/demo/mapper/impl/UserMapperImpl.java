package org.fomabb.demo.mapper.impl;

import org.fomabb.demo.dto.UserDataDto;
import org.fomabb.demo.dto.response.UserdataDtoResponse;
import org.fomabb.demo.entity.EmailData;
import org.fomabb.demo.entity.PhoneData;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDataDto entityToUserDataDto(User user) {
        return UserDataDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .dateOfBirth(user.getDateOfBirth())
                .primaryEmail(user.getPrimaryEmail())
                .build();
    }

    @Override
    public List<UserdataDtoResponse> listEntityUserToListUserDto(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream().map(user -> UserdataDtoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .primaryEmail(user.getPrimaryEmail())
                .dateOfBirth(user.getDateOfBirth())
                .emails(user.getEmailData().stream()
                        .map(EmailData::getEmail)
                        .toList())
                .phones(user.getPhoneData().stream()
                        .map(PhoneData::getPhone)
                        .toList())
                .build()).toList();
    }

    @Override
    public List<UserDataDto> listEntityToAllUserDto(List<User> content) {
        if (content == null) {
            return Collections.emptyList();
        }
        return content.stream().map(this::entityToUserDataDto).toList();
    }
}

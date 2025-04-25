package org.fomabb.demo.mapper.impl;

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
}

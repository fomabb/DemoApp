package org.fomabb.demo.service;

import org.fomabb.demo.dto.UserDataDto;
import org.fomabb.demo.dto.request.UpdateEmailRequest;
import org.fomabb.demo.dto.request.UpdatePhoneRequest;
import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.dto.response.PageableResponse;
import org.fomabb.demo.dto.response.PhoneDataDtoResponse;
import org.fomabb.demo.dto.response.UserdataDtoResponse;
import org.fomabb.demo.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface UserService {

    User findUserByEmail(String email);

    User findUserById(Long id);

    void addEmailToUser(Long userId, String email);

    void addPhoneToUser(Long userId, String phone);

    EmailDataDtoResponse getAllEmailsByUserId(Long id);

    PageableResponse<UserdataDtoResponse> search(String query, Pageable pageable, Date dateOfBirth);

    PageableResponse<UserDataDto> getAllUsers(Pageable pageable);

    UserDataDto getUserById(Long id);

    void updateEmail(UpdateEmailRequest dto);

    void updatePhone(UpdatePhoneRequest dto);

    void removePhoneByUserIdPhoneId(Long userId, Long phoneId);

    void removePhoneByUserIdEmailId(Long userId, Long emailId);

    PhoneDataDtoResponse getAllPhonesByUserId(Long id);
}

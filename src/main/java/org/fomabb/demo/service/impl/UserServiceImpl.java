package org.fomabb.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.demo.dto.UserDataDto;
import org.fomabb.demo.dto.request.UpdateEmailRequest;
import org.fomabb.demo.dto.request.UpdatePhoneRequest;
import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.dto.response.PageableResponse;
import org.fomabb.demo.dto.response.PhoneDataDtoResponse;
import org.fomabb.demo.dto.response.UserdataDtoResponse;
import org.fomabb.demo.entity.EmailData;
import org.fomabb.demo.entity.PhoneData;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.mapper.UserMapper;
import org.fomabb.demo.repository.UserRepository;
import org.fomabb.demo.security.enumeration.Role;
import org.fomabb.demo.security.service.UserServiceSecurity;
import org.fomabb.demo.service.EmailDataService;
import org.fomabb.demo.service.PhoneDataService;
import org.fomabb.demo.service.UserService;
import org.fomabb.demo.util.PageableResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.fomabb.demo.util.Constant.USER_DOES_NOT_HAVE_PERMISSION;
import static org.fomabb.demo.util.Constant.USER_WITH_EMAIL_NOT_FOUND;
import static org.fomabb.demo.util.Constant.USER_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailDataService emailDataService;
    private final PhoneDataService phoneDataService;
    private final UserServiceSecurity userServiceSecurity;
    private final UserMapper userMapper;
    private final PageableResponseUtil pageableResponseUtil;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByPrimaryEmail(email)
                .orElseGet(() -> userRepository.findByEmailDataEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, email))));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public void addEmailToUser(Long userId, String email) {
        User existingUser = findUserById(userId);
        Long validUserId = userServiceSecurity.getCurrentUser().getId();
        if (Objects.equals(existingUser.getId(), validUserId)) {
            emailDataService.emailDataSave(EmailData.builder()
                    .user(existingUser)
                    .email(email)
                    .build());
            log.info("Email адрес {} добавлен пользователю {}", email, existingUser.getName());
        } else {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }

    @Override
    @Transactional
    public void addPhoneToUser(Long userId, String phone) {
        User existingUser = findUserById(userId);
        Long validUserId = userServiceSecurity.getCurrentUser().getId();
        if (Objects.equals(existingUser.getId(), validUserId)) {
            phoneDataService.phoneDataSave(PhoneData.builder()
                    .user(existingUser)
                    .phone(phone)
                    .build());
            log.info("Телефон {} добавлен пользователю {}", phone, existingUser.getName());
        } else {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }

    @Override
    public EmailDataDtoResponse getAllEmailsByUserId(Long id) {
        Long userValid = userServiceSecurity.getCurrentUser().getId();
        boolean isAdmin = userServiceSecurity.getCurrentUser().getRole().equals(Role.ROLE_ADMIN);
        User existingUser = findUserById(id);
        if (Objects.equals(existingUser.getId(), userValid) || isAdmin) {
            return emailDataService.getEmailsByUserId(existingUser.getId());
        } else {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }

    @Override
    public PhoneDataDtoResponse getAllPhonesByUserId(Long id) {
        Long userValid = userServiceSecurity.getCurrentUser().getId();
        boolean isAdmin = userServiceSecurity.getCurrentUser().getRole().equals(Role.ROLE_ADMIN);
        User existingUser = findUserById(id);
        if (Objects.equals(existingUser.getId(), userValid) || isAdmin) {
            return phoneDataService.getResponsePhonesByUserId(existingUser.getId());
        } else {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }

    @Override
    public PageableResponse<UserdataDtoResponse> search(String query, Pageable pageable, Date dateOfBirth) {
        if (query == null || query.trim().isEmpty()) {
            return pageableResponseUtil.buildPageableResponse(
                    Collections.emptyList(), Page.empty(), new PageableResponse<>());
        }

        String trimmedQuery = query.trim();
        Page<User> userPage;

        if (dateOfBirth != null) {
            userPage = userRepository.searchByQueryWithDate(trimmedQuery, dateOfBirth, pageable);
        } else {
            userPage = userRepository.searchByQueryWithoutDate(trimmedQuery, pageable);
        }

        List<UserdataDtoResponse> dtoList = userMapper.listEntityUserToListUserDto(userPage.getContent());

        return pageableResponseUtil.buildPageableResponse(dtoList, userPage, new PageableResponse<>());
    }

    @Override
    public PageableResponse<UserDataDto> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserDataDto> userDataDtos = userMapper.listEntityToUserListDto(userPage.getContent());
        return pageableResponseUtil.buildPageableResponse(userDataDtos, userPage, new PageableResponse<>());
    }

    @Override
    public UserDataDto getUserById(Long id) {
        return userMapper.entityToUserDataDto(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, id))
        ));
    }

    @Override
    @Transactional
    public void updateEmail(UpdateEmailRequest dto) {
        User existingUser = findUserById(dto.getUserId());
        EmailData emailData = emailDataService.getEmailDataById(dto.getEmailId());

        Long validateUserId = userServiceSecurity.getCurrentUser().getId();

        if (Objects.equals(existingUser.getId(), validateUserId)
                && Objects.equals(existingUser.getId(), emailData.getId())
        ) {
            emailData.setEmail(dto.getEmail());

            if (dto.getEmail().equals(existingUser.getPrimaryEmail())) {
                existingUser.setPrimaryEmail(dto.getEmail());
            }

            emailDataService.emailDataSave(emailData);
        } else {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }

    @Override
    @Transactional
    public void updatePhone(UpdatePhoneRequest dto) {
        User existingUser = findUserById(dto.getUserId());
        PhoneData phoneData = phoneDataService.getPhoneDataById(dto.getPhoneId());

        Long validateUserId = userServiceSecurity.getCurrentUser().getId();

        if (Objects.equals(existingUser.getId(), validateUserId)
                && Objects.equals(existingUser.getId(), phoneData.getUser().getId())
        ) {
            phoneData.setPhone(dto.getPhone());
            phoneDataService.phoneDataSave(phoneData);
        } else {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }

    @Override
    @Transactional
    public void removePhoneByUserIdPhoneId(Long userId, Long phoneId) {
        User existingUser = findUserById(userId);
        PhoneData phoneData = phoneDataService.getPhoneDataById(phoneId);
        Long validateUserId = userServiceSecurity.getCurrentUser().getId();

        if (Objects.equals(existingUser.getId(), validateUserId)
                && Objects.equals(existingUser.getId(), phoneData.getUser().getId())) {
            List<PhoneData> userPhones = phoneDataService.getPhonesByUserId(existingUser.getId());
            if (userPhones.size() > 1) {
                phoneDataService.deletePhoneById(phoneData.getId());
            } else {
                throw new BusinessException("The user must have at least one phone");
            }
        } else {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }

    @Override
    @Transactional
    public void removePhoneByUserIdEmailId(Long userId, Long emailId) {
        User existingUser = findUserById(userId);
        EmailData emailData = emailDataService.getEmailDataById(emailId);
        Long validateUserId = userServiceSecurity.getCurrentUser().getId();

        if (Objects.equals(existingUser.getId(), validateUserId)
                && Objects.equals(existingUser.getId(), emailData.getUser().getId())) {
            List<EmailData> userEmail = emailDataService.getListEmailsByUserId(existingUser.getId());
            if (userEmail.size() > 1) {
                emailDataService.deleteEmailById(emailData.getId());
                existingUser.setPrimaryEmail(emailData.getEmail());
            } else {
                throw new BusinessException("The user must have at least one email");
            }
        } else {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }
}

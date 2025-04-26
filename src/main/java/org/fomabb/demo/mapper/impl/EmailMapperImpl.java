package org.fomabb.demo.mapper.impl;

import org.fomabb.demo.dto.EmailDataDto;
import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.entity.EmailData;
import org.fomabb.demo.mapper.EmailMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmailMapperImpl implements EmailMapper {

    @Override
    public EmailDataDtoResponse emailEntityToEmailResponse(Long userId, List<EmailData> emailData) {
        Set<EmailDataDto> emails = emailData.stream().map(item -> EmailDataDto.builder()
                .emailId(item.getId())
                .email(item.getEmail())
                .build()).collect(Collectors.toSet());

        return EmailDataDtoResponse.builder()
                .userId(userId)
                .emails(emails)
                .build();
    }
}

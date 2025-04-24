package org.fomabb.demo.mapper.impl;

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
    public EmailDataDtoResponse emailEntityToEmailResponse(List<EmailData> emailData) {

        Set<String> emails = emailData.stream()
                .map(EmailData::getEmail)
                .collect(Collectors.toSet());

        return EmailDataDtoResponse.builder()
                .emails(emails)
                .build();
    }
}

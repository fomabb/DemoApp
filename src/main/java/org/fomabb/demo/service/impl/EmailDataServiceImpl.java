package org.fomabb.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.entity.EmailData;
import org.fomabb.demo.mapper.Mapper;
import org.fomabb.demo.repository.EmailDataRepository;
import org.fomabb.demo.service.EmailDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailDataServiceImpl implements EmailDataService {

    private final EmailDataRepository emailDataRepository;
    private final Mapper mapper;

    @Override
    @Transactional
    public void emailDataSave(EmailData emailData) {
        emailDataRepository.save(emailData);
    }

    @Override
    public EmailDataDtoResponse getEmailsByUserId(Long userId) {
        return mapper.emailEntityToEmailResponse(emailDataRepository.findEmailDataByUserId(userId));
    }
}

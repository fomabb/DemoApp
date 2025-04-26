package org.fomabb.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.entity.EmailData;
import org.fomabb.demo.mapper.EmailMapper;
import org.fomabb.demo.repository.EmailDataRepository;
import org.fomabb.demo.service.EmailDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.fomabb.demo.util.Constant.EMAIL_DATA_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailDataServiceImpl implements EmailDataService {

    private final EmailDataRepository emailDataRepository;
    private final EmailMapper emailMapper;

    @Override
    @Transactional
    public void emailDataSave(EmailData emailData) {
        emailDataRepository.save(emailData);
    }

    @Override
    public EmailDataDtoResponse getEmailsByUserId(Long userId) {
        return emailMapper.emailEntityToEmailResponse(userId, emailDataRepository.findEmailDataByUserId(userId));
    }

    @Override
    public EmailData getEmailDataById(Long id) {
        return emailDataRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(EMAIL_DATA_WITH_ID_NOT_FOUND.formatted(id))
                );
    }

    @Override
    @Transactional
    public void deleteEmailById(Long id) {
        emailDataRepository.deleteById(id);
    }

    @Override
    public List<EmailData> getListEmailsByUserId(Long id) {
        return emailDataRepository.findEmailDataByUserId(id);
    }
}

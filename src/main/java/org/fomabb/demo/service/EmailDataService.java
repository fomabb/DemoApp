package org.fomabb.demo.service;

import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.entity.EmailData;

import java.util.List;

public interface EmailDataService {

    void emailDataSave(EmailData emailData);

    EmailDataDtoResponse getEmailsByUserId(Long userId);

    EmailData getEmailDataById(Long id);

    void deleteEmailById(Long id);

    List<EmailData> getListEmailsByUserId(Long id);
}

package org.fomabb.demo.service;

import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.entity.EmailData;

public interface EmailDataService {

    void emailDataSave(EmailData emailData);

    EmailDataDtoResponse getEmailsByUserId(Long userId);

    EmailData getEmailDataById(Long id);
}

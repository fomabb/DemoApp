package org.fomabb.demo.mapper;

import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.entity.EmailData;

import java.util.List;

public interface EmailMapper {

    EmailDataDtoResponse emailEntityToEmailResponse(List<EmailData> emailData);
}

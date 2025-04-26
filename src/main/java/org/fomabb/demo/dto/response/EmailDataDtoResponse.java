package org.fomabb.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.fomabb.demo.dto.EmailDataDto;

import java.util.Set;

@Getter
@Builder
@Schema(description = "DTO для ответа с данными email")
public class EmailDataDtoResponse {

    private Long userId;

    @Schema(description = "Набор адресов электронной почты", example = "[\"alice.smith@gmail.com\", \"alice.smith@example.com\"]")
    private Set<EmailDataDto> emails;
}

package org.fomabb.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@Schema(description = "DTO для ответа с данными email")
public class EmailDataDtoResponse {

    @Schema(description = "Набор адресов электронной почты", example = "[\"alice.smith@gmail.com\", \"alice.smith@example.com\"]")
    private Set<String> emails;
}

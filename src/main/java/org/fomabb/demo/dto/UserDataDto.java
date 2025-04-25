package org.fomabb.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@Schema(description = "DTO для данных пользователя")
public class UserDataDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long userId;

    @Schema(description = "Имя пользователя", example = "Alice Smith")
    private String name;

    @Schema(description = "Основной адрес электронной почты", example = "alice@example.com")
    private String primaryEmail;

    @Schema(description = "Дата рождения пользователя", example = "01.01.1990")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date dateOfBirth;
}

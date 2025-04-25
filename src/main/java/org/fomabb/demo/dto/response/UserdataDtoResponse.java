package org.fomabb.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для ответа с данными пользователя")
public class UserdataDtoResponse {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    private String name;

    @Schema(description = "Основной адрес электронной почты", example = "alice.smith@gmail.com")
    private String primaryEmail;

    @Schema(description = "Дата рождения пользователя", example = "01.01.1990")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date dateOfBirth;

    @Schema(description = "Список адресов электронной почты", example = "[\"alice.smith@gmail.com\", \"alice.smith@example.com\"]")
    private List<String> emails;

    @Schema(description = "Список номеров телефонов", example = "[\"79234567890\", \"79987654321\"]")
    private List<String> phones;
}

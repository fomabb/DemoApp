package org.fomabb.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Запрос на добавление адреса электронной почты пользователю")
public class UserAddEmailRequest {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long userId;

    @Schema(description = "Адрес электронной почты", example = "alice.smith@gmail.com")
    @Size(min = 5, max = 20, message = "Адрес электронной почты должен содержать от 5 до 200 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате user@gmail.com")
    private String email;
}

package org.fomabb.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Запрос на обновление номера телефона пользователя")
public class UpdatePhoneRequest {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long userId;

    @Schema(description = "Уникальный идентификатор номера телефона", example = "100")
    private Long phoneId;

    @Schema(description = "Номер телефона в формате 79XXXXXXXXX", example = "79123456789")
    @Pattern(regexp = "^79[0-9]{9}$")
    @Size(min = 11, message = "Телефон должен содержать должен содержать 13 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    private String phone;
}

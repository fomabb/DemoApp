package org.fomabb.demo.security.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Адрес электронной почты", example = "alice@example.com")
    @Size(min = 5, max = 200, message = "Адрес электронной почты должен содержать от 5 до 200 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате user@gmail.com")
    private String email;

    @Schema(description = "Пароль", example = "user")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}

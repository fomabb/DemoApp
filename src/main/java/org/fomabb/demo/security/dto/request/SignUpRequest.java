package org.fomabb.demo.security.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    @Size(min = 2, max = 50, message = "Имя пользователя должно содержать от 2 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;

    @Schema(description = "Дата рождения пользователя", example = "01.05.1993")
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Schema(description = "Адрес электронной почты", example = "ivan123@gmail.com")
    @Size(min = 5, max = 100, message = "Адрес электронной почты должен содержать от 5 до 100 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате user@gmail.com")
    private String email;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Schema(description = "Подтверждение пароля", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String confirmPassword;
}

package org.fomabb.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePhoneRequest {

    private Long userId;

    private Long phoneId;

    @Pattern(regexp = "^79[0-9]{9}$")
    @Size(min = 11, message = "Телефон должен содержать должен содержать 13 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    private String phone;
}

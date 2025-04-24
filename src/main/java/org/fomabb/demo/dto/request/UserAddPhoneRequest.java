package org.fomabb.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddPhoneRequest {

    private Long userId;

    @Schema(description = "Номер телефона", example = "79207865432")
    @Size(min = 11, message = "Телефон должен содержать должен содержать 13 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Pattern(regexp = "^79[0-9]{9}$")
    private String phone;
}

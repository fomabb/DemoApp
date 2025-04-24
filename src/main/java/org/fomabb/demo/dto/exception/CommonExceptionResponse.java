package org.fomabb.demo.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ответ исключения")
public class CommonExceptionResponse {

    @Schema(description = "Время выброса исключения")
    private LocalDateTime timestamp;

    @Schema(description = "Название класса исключения")
    private String exceptionClass;

    @Schema(description = "Сообщение об исключении")
    private String message;
}

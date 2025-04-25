package org.fomabb.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Перевод денежных средств между счетами пользователей")
public class TransferDtoRequest {

    @Schema(description = "Идентификатор отправителя", example = "1")
    private Long transferFrom;

    @Schema(description = "Идентификатор получателя", example = "2")
    private Long transferTo;

    @Schema(description = "Сумма денежного перевод", example = "1000")
    @Positive(message = "The amount cannot be negative")
    private BigDecimal transferAmount;
}

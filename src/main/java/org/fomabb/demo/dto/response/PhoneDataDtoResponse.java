package org.fomabb.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.fomabb.demo.dto.PhoneDataDto;

import java.util.Set;

@Getter
@Builder
@Schema(description = "DTO для ответа с данными email")
public class PhoneDataDtoResponse {

    private Long userId;

    @Schema(description = "Набор телефонных номеров", example = "[\"79123567653134\", \"79463567651251\"]")
    private Set<PhoneDataDto> phones;
}

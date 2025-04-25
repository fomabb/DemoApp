package org.fomabb.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@Schema(description = "DTO для ответа с данными email")
public class PhoneDataDtoResponse {

    @Schema(description = "Набор телефонных номеров", example = "[\"79123567653134\", \"79463567651251\"]")
    private Set<String> phones;
}

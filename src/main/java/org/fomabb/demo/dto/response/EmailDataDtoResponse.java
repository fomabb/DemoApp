package org.fomabb.demo.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class EmailDataDtoResponse {

    private Set<String> emails;
}

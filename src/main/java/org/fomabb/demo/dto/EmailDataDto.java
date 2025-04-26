package org.fomabb.demo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailDataDto {

    private Long emailId;
    private String email;
}

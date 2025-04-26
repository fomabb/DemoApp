package org.fomabb.demo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhoneDataDto {

    private Long phoneId;
    private String phone;
}

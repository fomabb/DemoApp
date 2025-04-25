package org.fomabb.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class UserDataDto {

    private Long userId;

    private String name;

    private String primaryEmail;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date dateOfBirth;
}

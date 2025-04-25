package org.fomabb.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserdataDtoResponse {

    private Long id;

    private String name;

    private String primaryEmail;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date dateOfBirth;

    private List<String> emails;

    private List<String> phones;
}

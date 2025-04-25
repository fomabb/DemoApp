package org.fomabb.demo.dto.response;

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

    private Date dateOfBirth;

    private List<String> emails;

    private List<String> phones;
}

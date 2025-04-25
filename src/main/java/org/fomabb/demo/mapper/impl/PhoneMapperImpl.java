package org.fomabb.demo.mapper.impl;

import org.fomabb.demo.dto.response.PhoneDataDtoResponse;
import org.fomabb.demo.entity.PhoneData;
import org.fomabb.demo.mapper.PhoneMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PhoneMapperImpl implements PhoneMapper {
    @Override
    public PhoneDataDtoResponse phonesEntityToPhoneResponse(List<PhoneData> phoneData) {
        Set<String> emails = phoneData.stream()
                .map(PhoneData::getPhone)
                .collect(Collectors.toSet());

        return PhoneDataDtoResponse.builder()
                .phones(emails)
                .build();
    }
}

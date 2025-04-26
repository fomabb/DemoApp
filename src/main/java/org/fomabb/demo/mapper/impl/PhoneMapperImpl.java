package org.fomabb.demo.mapper.impl;

import org.fomabb.demo.dto.PhoneDataDto;
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
    public PhoneDataDtoResponse phonesEntityToPhoneResponse(Long userId, List<PhoneData> phoneData) {
        Set<PhoneDataDto> emails = phoneData.stream()
                .map(item -> PhoneDataDto.builder()
                        .phoneId(item.getId())
                        .phone(item.getPhone())
                        .build())
                .collect(Collectors.toSet());

        return PhoneDataDtoResponse.builder()
                .userId(userId)
                .phones(emails)
                .build();
    }
}

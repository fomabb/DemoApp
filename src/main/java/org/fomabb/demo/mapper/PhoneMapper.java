package org.fomabb.demo.mapper;

import org.fomabb.demo.dto.response.PhoneDataDtoResponse;
import org.fomabb.demo.entity.PhoneData;

import java.util.List;

public interface PhoneMapper {

    PhoneDataDtoResponse phonesEntityToPhoneResponse(List<PhoneData> phoneData);
}

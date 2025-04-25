package org.fomabb.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.demo.dto.response.PhoneDataDtoResponse;
import org.fomabb.demo.entity.PhoneData;
import org.fomabb.demo.mapper.PhoneMapper;
import org.fomabb.demo.repository.PhoneDataRepository;
import org.fomabb.demo.service.PhoneDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.fomabb.demo.util.Constant.PHONE_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhoneDataServiceImpl implements PhoneDataService {

    private final PhoneDataRepository phoneDataRepository;
    private final PhoneMapper phoneMapper;

    @Override
    @Transactional
    public void phoneDataSave(PhoneData phoneData) {
        phoneDataRepository.save(phoneData);
    }

    @Override
    public PhoneData getPhoneDataById(Long phoneId) {
        return phoneDataRepository.findById(phoneId).orElseThrow(
                () -> new EntityNotFoundException(String.format(PHONE_WITH_ID_NOT_FOUND, phoneId))
        );
    }

    @Override
    @Transactional
    public void deletePhoneById(Long id) {
        phoneDataRepository.deleteById(id);
    }

    @Override
    public List<PhoneData> getPhonesByUserId(Long userId) {
        return phoneDataRepository.findByUserId(userId);
    }

    @Override
    public PhoneDataDtoResponse getResponsePhonesByUserId(Long userId) {
        return phoneMapper.phonesEntityToPhoneResponse(phoneDataRepository.findByUserId(userId));
    }
}

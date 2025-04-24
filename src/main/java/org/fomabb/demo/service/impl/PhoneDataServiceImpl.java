package org.fomabb.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.fomabb.demo.entity.PhoneData;
import org.fomabb.demo.repository.PhoneDataRepository;
import org.fomabb.demo.service.PhoneDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhoneDataServiceImpl implements PhoneDataService {

    private final PhoneDataRepository phoneDataRepository;

    @Override
    @Transactional
    public void phoneDataSave(PhoneData phoneData) {
        phoneDataRepository.save(phoneData);
    }
}

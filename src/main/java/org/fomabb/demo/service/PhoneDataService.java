package org.fomabb.demo.service;

import org.fomabb.demo.entity.PhoneData;

import java.util.List;

public interface PhoneDataService {

    void phoneDataSave(PhoneData phoneData);

    PhoneData getPhoneDataById(Long phoneId);

    void deletePhoneById(Long id);

    List<PhoneData> getPhonesByUserId(Long userId);
}

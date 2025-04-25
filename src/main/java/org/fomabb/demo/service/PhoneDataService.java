package org.fomabb.demo.service;

import org.fomabb.demo.entity.PhoneData;

public interface PhoneDataService {

    void phoneDataSave(PhoneData phoneData);

    PhoneData getPhoneDataById(Long phoneId);
}

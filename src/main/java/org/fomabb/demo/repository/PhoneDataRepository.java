package org.fomabb.demo.repository;

import org.fomabb.demo.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    List<PhoneData> findByUserId(Long userId);
}

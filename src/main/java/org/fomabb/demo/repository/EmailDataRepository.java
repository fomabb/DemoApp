package org.fomabb.demo.repository;

import org.fomabb.demo.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

    List<EmailData> findEmailDataByUserId(Long userId);
}

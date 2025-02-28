package com.stamp.api.attendance.repository;

import com.stamp.api.attendance.entity.Attendance;
import com.stamp.api.attendance.entity.QRAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QRAuthCodeRepository extends JpaRepository<QRAuthCode, Long> {

    boolean existsByStoreId(Long storeId);
    Optional<QRAuthCode> findByStoreId(Long storeId);
}

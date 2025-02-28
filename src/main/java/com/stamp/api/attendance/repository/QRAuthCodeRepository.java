package com.stamp.api.attendance.repository;

import com.stamp.api.attendance.entity.QRAuthCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRAuthCodeRepository extends JpaRepository<QRAuthCode, Long> {

  boolean existsByStoreId(Long storeId);

  Optional<QRAuthCode> findByStoreId(Long storeId);
}

package com.stamp.api.store.repository;

import com.stamp.api.store.entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
  Optional<Store> findByBusinessNumber(String businessNumber);
}

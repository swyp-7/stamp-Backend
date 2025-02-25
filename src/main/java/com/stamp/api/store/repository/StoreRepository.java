package com.stamp.api.store.repository;

import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.store.entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {
  Optional<Store> findByBusinessNumber(String businessNumber);

  @Query(
      "SELECT s FROM Store s LEFT JOIN FETCH s.storeScheduleList WHERE s.id = :storeId AND s.deletedAt IS NULL")
  Optional<Store> findByIdWithSchedules(@Param("storeId") Long storeId);

  @Query(
      "SELECT s FROM Store s LEFT JOIN FETCH s.storeScheduleList "
          + "WHERE s.employerUser = :employerUser AND s.deletedAt IS NULL")
  Optional<Store> findByEmployerUserIdWithSchedules(
      @Param("employerUser") EmployerUser employerUser);
}

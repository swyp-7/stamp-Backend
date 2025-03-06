package com.stamp.api.extraShift.repository;

import com.stamp.api.extraShift.entity.ExtraShift;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraShiftRepository extends JpaRepository<ExtraShift, Long> {
  List<ExtraShift> findAllByEmployeeId(Long employeeId);

  Optional<ExtraShift> findByEmployeeIdAndRequestDate(Long employeeId, LocalDate requestDate);
}

package com.stamp.api.extraShift.repository;

import com.stamp.api.extraShift.entity.ExtraShift;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraShiftRepository extends JpaRepository<ExtraShift, Long> {
  List<ExtraShift> findAllByEmployeeId(Long employeeId);
}

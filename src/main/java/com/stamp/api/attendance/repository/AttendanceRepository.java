package com.stamp.api.attendance.repository;

import com.stamp.api.attendance.entity.Attendance;
import com.stamp.api.attendance.entity.AttendanceEnum;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {

  boolean existsAttendanceByStoreIdAndEmployeeIdAndDateAndAttendance(
      Long storeId, Long employeeId, LocalDate date, AttendanceEnum attendance);
}

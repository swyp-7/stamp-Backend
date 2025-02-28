package com.stamp.api.attendance.repository;

import com.stamp.api.attendance.entity.Attendance;
import com.stamp.api.attendance.entity.AttendanceEnum;
import com.stamp.api.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {

    boolean existsAttendanceByStoreIdAndEmployeeIdAndDateAndAttendance(Long storeId, Long employeeId, LocalDate date, AttendanceEnum attendance);
}

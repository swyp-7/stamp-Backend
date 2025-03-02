package com.stamp.api.attendance.repository;

import com.stamp.api.attendance.dto.response.AttendanceRes;
import com.stamp.api.attendance.entity.Attendance;
import com.stamp.api.attendance.entity.AttendanceEnum;
import com.stamp.api.employee.dto.response.EmployeeAttendacneRes;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {

  boolean existsAttendanceByStoreIdAndEmployeeIdAndDateAndAttendance(
      Long storeId, Long employeeId, LocalDate date, AttendanceEnum attendance);

  /**
   * Attendance의 Id값을 이용하여 출퇴근 로그 조회, AttendanceRes의 형식에 맞춰 반환. Id값을 통해 조회할 로그의 대상 store와 날짜 범위를 조절
   * 가능하다.
   */
  @Query(
      "SELECT new com.stamp.api.attendance.dto.response.AttendanceRes(a.id, e.name, e.id, a.attendance, a.date, a.time) "
          + "FROM Attendance a "
          + "LEFT JOIN Employee e ON a.employeeId = e.id "
          + "WHERE a.id >= :id1 "
          + "AND a.id < :id2")
  List<AttendanceRes> findAttendancesByIdRange(@Param("id1") String id1, @Param("id2") String id2);

  /**
   * Attendance의 Id값을 이용하여 출퇴근 로그 조회, AttendanceRes의 형식에 맞춰 반환. Id값을 통해 조회할 로그의 대상 store와 날짜 범위를 조절
   * 가능하다. 특정 Employee의 로그만을 조회한다.
   */
  @Query(
      "SELECT new com.stamp.api.attendance.dto.response.AttendanceRes(a.id, e.name, e.id, a.attendance, a.date, a.time) "
          + "FROM Attendance a "
          + "LEFT JOIN Employee e ON a.employeeId = e.id "
          + "WHERE a.id >= :id1 "
          + "AND a.id < :id2 "
          + "AND a.employeeId = :employeeId")
  List<AttendanceRes> findAttendancesByIdRangeAndEmployeeId(
      @Param("id1") String id1, @Param("id2") String id2, @Param("employeeId") Long employeeId);

  /**
   * Attendance의 Id값을 이용하여 출퇴근 로그 조회, AttendanceRes의 형식에 맞춰 반환. Id값을 통해 조회할 로그의 대상 store와 날짜 범위를 조절
   * 가능하다. 특정 Employee의 로그만을 조회한다.
   */
  @Query(
      "SELECT new com.stamp.api.employee.dto.response.EmployeeAttendacneRes(a.id, a.attendance, a.date, a.time) "
          + "FROM Attendance a "
          + "WHERE a.id >= :id1 "
          + "AND a.id < :id2 "
          + "AND a.employeeId = :employeeId")
  List<EmployeeAttendacneRes> findAttendancesByIdRangeOnEmployee(
      @Param("id1") String id1, @Param("id2") String id2, @Param("employeeId") Long employeeId);
}

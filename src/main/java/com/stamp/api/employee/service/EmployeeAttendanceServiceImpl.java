package com.stamp.api.employee.service;

import com.stamp.api.attendance.repository.AttendanceRepository;
import com.stamp.api.employee.dto.response.EmployeeAttendacneRes;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.exception.EmployeeErrorCode;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.global.exception.DomainException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeAttendanceServiceImpl implements EmployeeAttendanceService {

  private final AttendanceRepository attendanceRepository;
  private final EmployeeRepository employeeRepository;

  @Override
  public List<EmployeeAttendacneRes> getAttendancesForMonth(
      LocalDate firstDate, UserDetails userDetails) {

    Long employeeId = Long.parseLong(userDetails.getUsername());
    Employee employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(
                () ->
                    new DomainException(
                        EmployeeErrorCode.EMPLOYEE_NOT_FOUND,
                        "EmployeeAttendanceServiceImpl.getAttendanceForMonth"));
    Long storeId = employee.getStore().getId();

    // firstDate로 Attendance Id 생성
    String id1 = createAttendanceId(storeId, firstDate);
    // firstDate + 1개월의 날짜로 Attendance Id 생성
    String id2 = createAttendanceId(storeId, firstDate.plusMonths(1));

    return attendanceRepository.findAttendancesByIdRangeOnEmployee(id1, id2, employeeId);
  }

  @Override
  public List<EmployeeAttendacneRes> getAttendancesForDay(
      LocalDate firstDate, UserDetails userDetails) {

    Long employeeId = Long.parseLong(userDetails.getUsername());
    Employee employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(
                () ->
                    new DomainException(
                        EmployeeErrorCode.EMPLOYEE_NOT_FOUND,
                        "EmployeeAttendanceServiceImpl.getAttendancesForDay"));
    Long storeId = employee.getStore().getId();

    // firstDate로 Attendance Id 생성
    String id1 = createAttendanceId(storeId, firstDate);
    // firstDate + 1일의 날짜로 Attendance Id 생성
    String id2 = createAttendanceId(storeId, firstDate.plusDays(1));

    return attendanceRepository.findAttendancesByIdRangeOnEmployee(id1, id2, employeeId);
  }

  /** 출/퇴근 로그 정보를 Attendance Id로 변환하는 메서드 */
  private static String createAttendanceId(Long storeId, LocalDate firstDate) {
    return String.format("%019d", storeId)
        + firstDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }
}

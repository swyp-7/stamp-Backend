package com.stamp.api.store.service;

import com.stamp.api.attendance.dto.response.AttendanceRes;
import com.stamp.api.attendance.entity.AttendanceEnum;
import com.stamp.api.attendance.exception.AttendanceErrorCode;
import com.stamp.api.attendance.service.AttendanceService;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.entity.IsPaid;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employee.repository.IsPaidRepository;
import com.stamp.api.store.dto.request.WageStatusReq;
import com.stamp.api.store.dto.response.WageRes;
import com.stamp.global.exception.DomainException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WageServiceImpl implements WageService {

  private final AttendanceService attendanceService;
  private final EmployeeRepository employeeRepository;
  private final IsPaidRepository isPaidRepository;

  @Override
  public List<WageRes> getEmployeeWage(LocalDate firstDate, UserDetails userDetails) {

    // 1) 특정 달의 AttendanceRes 목록 조회
    List<AttendanceRes> attendanceResList =
        attendanceService.getEmployeesWorktimeForMonth(firstDate, userDetails);

    // 직원별 총 근무 시간을 저장할 맵
    HashMap<Long, Duration> timeMap = new HashMap<>();
    // (직원ID+날짜) -> 퇴근기록(AttendanceRes)를 매핑할 맵
    HashMap<String, AttendanceRes> punchOutMap = new HashMap<>();

    // 2-1) 먼저 퇴근 기록(PUNCH_OUT)을 Map에 저장
    for (AttendanceRes attendanceRes : attendanceResList) {
      // 직원별 근무 시간 계산용으로 초기값이 없으면 추가
      timeMap.putIfAbsent(attendanceRes.employeeId(), Duration.ZERO);

      if (attendanceRes.attendanceEnum().equals(AttendanceEnum.PUNCH_OUT)) {
        // key: "직원ID + 날짜", value: 퇴근 기록
        String key = attendanceRes.employeeId().toString() + attendanceRes.date();
        punchOutMap.put(key, attendanceRes);
      }
    }

    // 2-2) 출근(PUNCH_IN)에 대해서 퇴근기록을 매칭하여 근무 시간 계산
    for (AttendanceRes attendanceRes : attendanceResList) {
      if (attendanceRes.attendanceEnum().equals(AttendanceEnum.PUNCH_IN)) {
        String key = attendanceRes.employeeId().toString() + attendanceRes.date();
        AttendanceRes punchOutRes = punchOutMap.get(key);
        if (punchOutRes == null) continue; // 해당 날짜의 퇴근 기록이 없으면 패스

        // 기존에 누적되어 있던 근무 시간
        Duration accumulatedDuration = timeMap.get(attendanceRes.employeeId());

        // 출근 시각 ~ 퇴근 시각 사이의 차이
        Duration workDuration = Duration.between(attendanceRes.time(), punchOutRes.time());

        // 누적 근무 시간에 더해서 다시 저장
        timeMap.put(attendanceRes.employeeId(), accumulatedDuration.plus(workDuration));
      }
    }

    List<WageRes> wageResList = new ArrayList<>();

    for (Map.Entry<Long, Duration> entry : timeMap.entrySet()) {
      Long employeeId = entry.getKey();
      Duration totalDuration = entry.getValue();

      Employee employee =
          employeeRepository
              .findById(employeeId)
              .orElseThrow(
                  () ->
                      new DomainException(
                          AttendanceErrorCode.EMPLOYEE_ID_ERROR, employeeId.toString()));

      int hourlyPay = Integer.parseInt(employee.getWage());
      long totalMinutes = totalDuration.toMinutes();
      double totalHours = totalMinutes / 60.0;
      int totalWage = (int) (totalHours * hourlyPay);
      String name = employee.getName();

      boolean isPaid =
          isPaidRepository
              .findByEmployeeAndDate(
                  employee, LocalDate.of(firstDate.getYear(), firstDate.getMonth(), 1))
              .map(IsPaid::isPaid)
              .orElse(false);

      WageRes wageRes = new WageRes(employeeId, name, totalDuration, hourlyPay, totalWage, isPaid);
      wageResList.add(wageRes);
    }
    return wageResList;
  }

  @Override
  public Void updateEmplyeeWageStatus(WageStatusReq wageStatusReq, LocalDate firstDate) {
    Employee employee =
        employeeRepository.findById(Long.valueOf(wageStatusReq.employeeId())).orElseThrow();
    IsPaid isPaid =
        isPaidRepository
            .findByEmployeeAndDate(
                employee, LocalDate.of(firstDate.getYear(), firstDate.getMonth(), 1))
            .orElse(IsPaid.of(employee, firstDate, wageStatusReq.isPaid()));
    isPaid.setPaid(wageStatusReq.isPaid());
    isPaidRepository.save(isPaid);
    return null;
  }
}

package com.stamp.api.extraShift.service;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.exception.EmployeeErrorCode;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import com.stamp.api.employeeschedule.repository.EmployeeScheduleRepository;
import com.stamp.api.extraShift.dto.request.ExtraShiftReq;
import com.stamp.api.extraShift.entity.ExtraShift;
import com.stamp.api.extraShift.entity.RequestStatus;
import com.stamp.api.extraShift.exception.ExtraShiftErrorCode;
import com.stamp.api.extraShift.repository.ExtraShiftRepository;
import com.stamp.global.exception.DomainException;
import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateExtraShiftServiceImpl implements CreateExtraShiftService {
  private final ExtraShiftRepository extraShiftRepository;
  private final EmployeeRepository employeeRepository;
  private final EmployeeScheduleRepository employeeScheduleRepository;

  @Transactional
  @Override
  public void createExtraShiftRequest(Long storeId, Long employeeId, ExtraShiftReq extraShiftReq) {
    Employee employee = findEmployeeById(employeeId);

    LocalDate requestDate = extraShiftReq.requestDate();
    WeekDay requestWeekDay = convertToWeekDay(requestDate);

    boolean isAvailable = checkEmployeeAvailability(employee, requestWeekDay);
    if (!isAvailable) {
      extraShiftRepository.save(ExtraShift.of(requestDate, RequestStatus.REQUESTED, employee));
    } else {
      throw new DomainException(
          ExtraShiftErrorCode.EXTRA_SHIFT_BAD_REQUEST,
          "ExtraShiftServiceImpl.createExtraShiftRequest");
    }
  }

  private Employee findEmployeeById(Long employeeId) {
    return employeeRepository
        .findById(employeeId)
        .orElseThrow(
            () ->
                new DomainException(
                    EmployeeErrorCode.EMPLOYEE_NOT_FOUND,
                    "ExtraShiftServiceImpl.createExtraShiftRequest"));
  }

  private WeekDay convertToWeekDay(LocalDate date) {
    DayOfWeek dayOfWeek = date.getDayOfWeek();

    return switch (dayOfWeek) {
      case MONDAY -> WeekDay.MONDAY;
      case TUESDAY -> WeekDay.TUESDAY;
      case WEDNESDAY -> WeekDay.WEDNESDAY;
      case THURSDAY -> WeekDay.THURSDAY;
      case FRIDAY -> WeekDay.FRIDAY;
      case SATURDAY -> WeekDay.SATURDAY;
      case SUNDAY -> WeekDay.SUNDAY;
    };
  }

  /**
   * 직원의 특정 요일 가용성 확인
   *
   * @return true: 해당 요일에 스케줄이 존재 (추가 근무 가능), false: 해당 요일에 스케줄이 없음 (추가 근무 불가)
   */
  private boolean checkEmployeeAvailability(Employee employee, WeekDay weekDay) {
    List<EmployeeSchedule> schedules =
        employeeScheduleRepository.findByEmployeeAndWeekDay(employee, weekDay);
    return schedules.isEmpty();
  }
}

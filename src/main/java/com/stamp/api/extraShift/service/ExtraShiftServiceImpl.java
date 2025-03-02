package com.stamp.api.extraShift.service;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.exception.EmployeeErrorCode;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import com.stamp.api.employeeschedule.repository.EmployeeScheduleRepository;
import com.stamp.api.extraShift.dto.request.ExtraShiftReq;
import com.stamp.api.extraShift.dto.response.ExtraShiftRes;
import com.stamp.api.extraShift.entity.ExtraShift;
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
public class ExtraShiftServiceImpl implements ExtraShiftService {

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
    if (isAvailable) {
      extraShiftRepository.save(ExtraShift.of(requestDate, false, employee));
    } else {
      throw new DomainException(
          ExtraShiftErrorCode.EXTRA_SHIFT_BAD_REQUEST,
          "ExtraShiftServiceImpl.createExtraShiftRequest");
    }
  }

  @Override
  public List<ExtraShiftRes> getExtraShiftRequests(Long employeeId) {
    return extraShiftRepository.findAllByEmployeeId(employeeId).stream()
        .map(ExtraShiftRes::of)
        .toList();
  }

  @Transactional
  @Override
  public ExtraShiftRes acceptExtraShiftRequest(Long extraShiftId, Long employeeId) {
    return changeStatus(extraShiftId, employeeId, "accept");
  }

  @Transactional
  @Override
  public ExtraShiftRes rejectExtraShiftRequest(Long extraShiftId, Long employeeId) {
    return changeStatus(extraShiftId, employeeId, "reject");
  }

  private ExtraShiftRes changeStatus(Long extraShiftId, Long employeeId, String acceptOrReject) {

    ExtraShift extraShift = findExtraShiftById(extraShiftId);

    if (checkEmployee(extraShift, employeeId)) {
      if (acceptOrReject.equals("accept")) {
        extraShift.accept();
      } else {
        extraShift.reject();
      }
    } else {
      throw new DomainException(
          ExtraShiftErrorCode.NO_AUTHORITY_FOR_ACCEPT_REQUEST,
          "ExtraShiftServiceImpl.acceptExtraShiftRequest");
    }
    return ExtraShiftRes.of(extraShift);
  }

  private ExtraShift findExtraShiftById(Long extraShiftId) {
    return extraShiftRepository
        .findById(extraShiftId)
        .orElseThrow(
            () ->
                new DomainException(
                    ExtraShiftErrorCode.EXTRA_SHIFT_NOT_FOUND,
                    "ExtraShiftServiceImpl.createExtraShiftRequest"));
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

  private boolean checkEmployee(ExtraShift extraShift, Long employeeId) {
    return extraShift.getEmployee().getId().equals(employeeId);
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
   * @return true: 해당 요일에 스케줄이 없음 (추가 근무 가능), false: 해당 요일에 스케줄이 있음 (추가 근무 불가)
   */
  private boolean checkEmployeeAvailability(Employee employee, WeekDay weekDay) {
    List<EmployeeSchedule> schedules =
        employeeScheduleRepository.findByEmployeeAndWeekDay(employee, weekDay);
    return schedules.isEmpty();
  }
}

package com.stamp.api.employee.service;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.dto.response.ReadEmployeeRes;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.exception.EmployeeErrorCode;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.exception.StoreErrorCode;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.global.exception.DomainException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadEmployeeServiceImpl implements ReadEmployeeService {

  private final EmployeeRepository employeeRepository;
  private final StoreRepository storeRepository;

  @Override
  public ReadEmployeeRes getEmployee(Long employeeId) {
    Employee employee = findEmployee(employeeId);
    return ReadEmployeeRes.of(employee);
  }

  @Override
  public List<ReadEmployeeRes> getAllEmployees(Long storeId) {
    return findAllEmployees(storeId).stream().map(ReadEmployeeRes::of).toList();
  }

  @Override
  public List<ReadEmployeeRes> getEmployeeByPeriod(
      Long storeId, LocalDate startDate, LocalDate endDate) {
    Store store = findStore(storeId);

    List<WeekDay> weekDays = getWeekDaysForPeriod(startDate, endDate);
    return employeeRepository
        .findActiveEmployeesByPeriod(
            store, endDate, // 현재 근무 중인 직원 체크용
            weekDays)
        .stream()
        .map((ReadEmployeeRes::of))
        .toList();
  }

  private Employee findEmployee(Long employeeId) {
    return employeeRepository
        .findByIdWithSchedules(employeeId)
        .orElseThrow(
            () ->
                new DomainException(
                    EmployeeErrorCode.EMPLOYEE_NOT_FOUNDED, "ReadEmployeeServiceImpl.getEmployee"));
  }

  private List<Employee> findAllEmployees(Long storeId) {
    return employeeRepository.findAllByStoreWithSchedules(findStore(storeId));
  }

  private Store findStore(Long storeId) {
    return storeRepository
        .findByIdWithSchedules(storeId)
        .orElseThrow(
            () ->
                new DomainException(
                    StoreErrorCode.STORE_NOT_FOUNDED, "ReadEmployeeServiceImpl.getEmployee"));
  }

  private List<WeekDay> getWeekDaysForPeriod(LocalDate startDate, LocalDate endDate) {
    List<WeekDay> weekDays = new ArrayList<>();
    LocalDate currentDate = startDate;

    while (!currentDate.isAfter(endDate)) {
      WeekDay weekDay = convertToWeekDay(currentDate.getDayOfWeek());
      if (!weekDays.contains(weekDay)) {
        weekDays.add(weekDay);
      }
      currentDate = currentDate.plusDays(1);
    }

    weekDays.sort(Comparator.comparingInt(WeekDay::getSequence));
    return weekDays;
  }

  private WeekDay convertToWeekDay(DayOfWeek dayOfWeek) {
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
}

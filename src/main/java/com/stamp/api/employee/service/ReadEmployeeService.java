package com.stamp.api.employee.service;

import com.stamp.api.employee.dto.response.ReadEmployeeRes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReadEmployeeService {
  ReadEmployeeRes getEmployee(Long employeeId);

  List<ReadEmployeeRes> getAllEmployees(Long storeId);

  List<ReadEmployeeRes> getEmployeeByPeriod(Long storeId, LocalDate startDate, LocalDate endDate);

  List<ReadEmployeeRes> getAvailableEmployeesForTimeSlot(
      Long storeId, LocalDate date, LocalTime startTime, LocalTime endTime);
}

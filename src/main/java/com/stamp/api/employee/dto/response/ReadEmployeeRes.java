package com.stamp.api.employee.dto.response;

import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employeeschedule.dto.response.ReadEmployeeScheduleRes;
import java.time.LocalDate;
import java.util.List;

public record ReadEmployeeRes(
    Long employeeId,
    String name,
    LocalDate birthDate,
    String contact,
    String addressCommon,
    String addressDetail,
    String bank,
    String bankAccountNumber,
    String wage,
    LocalDate startDate,
    LocalDate endDate,
    List<ReadEmployeeScheduleRes> scheduleList) {
  public static ReadEmployeeRes of(Employee employee) {
    return new ReadEmployeeRes(
        employee.getId(),
        employee.getName(),
        employee.getBirthDate(),
        employee.getContact(),
        employee.getAddressCommon(),
        employee.getAddressDetail(),
        employee.getBank(),
        employee.getBankAccountNumber(),
        employee.getWage(),
        employee.getStartDate(),
        employee.getEndDate(),
        employee.getEmployeeScheduleList().stream().map(ReadEmployeeScheduleRes::of).toList());
  }
}

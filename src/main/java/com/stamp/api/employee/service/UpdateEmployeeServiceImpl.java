package com.stamp.api.employee.service;

import com.stamp.api.employee.dto.request.UpdateEmployeeReq;
import com.stamp.api.employee.dto.response.ReadEmployeeRes;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.exception.EmployeeErrorCode;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeeschedule.dto.request.CreateEmployeeScheduleReq;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import com.stamp.api.employeeschedule.repository.EmployeeScheduleRepository;
import com.stamp.global.exception.DomainException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateEmployeeServiceImpl implements UpdateEmployeeService {
  private final EmployeeRepository employeeRepository;
  private final EmployeeScheduleRepository employeeScheduleRepository;

  @Transactional
  @Override
  public ReadEmployeeRes updateEmployee(Long employeeId, UpdateEmployeeReq updateEmployeeReq) {
    Employee employee = findEmployee(employeeId);
    employee.update(updateEmployeeReq);

    updateEmployeeReq
        .scheduleList()
        .forEach(
            scheduleReq -> {
              if (scheduleReq.id() == null) {
                // 새로운 스케줄 추가
                CreateEmployeeScheduleReq createScheduleReq =
                    CreateEmployeeScheduleReq.of(
                        scheduleReq.weekDay(),
                        scheduleReq.startTime(),
                        scheduleReq.endTime(),
                        scheduleReq.isAdditional());
                employeeScheduleRepository.save(EmployeeSchedule.of(createScheduleReq, employee));
              } else {
                // 기존 스케줄 업데이트
                employeeScheduleRepository
                    .findById(scheduleReq.id())
                    .orElseThrow(
                        () ->
                            new DomainException(
                                EmployeeErrorCode.EMPLOYEE_SCHEDULE_NOT_FOUNDED,
                                "UpdateEmployeeServiceImpl.updateEmployee"))
                    .update(scheduleReq);
              }
            });
    return ReadEmployeeRes.of(employee);
  }

  private Employee findEmployee(Long employeeId) {
    return employeeRepository
        .findByIdWithSchedules(employeeId)
        .orElseThrow(
            () ->
                new DomainException(
                    EmployeeErrorCode.EMPLOYEE_NOT_FOUND,
                    "UpdateEmployeeServiceImpl.updateEmployee"));
  }
}

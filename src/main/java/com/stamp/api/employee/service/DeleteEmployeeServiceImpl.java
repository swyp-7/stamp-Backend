package com.stamp.api.employee.service;

import com.stamp.api.employeeschedule.repository.EmployeeScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteEmployeeServiceImpl implements DeleteEmployeeService {

  private final EmployeeScheduleRepository employeeScheduleRepository;

  @Transactional
  @Override
  public void deleteEmployeeSchedule(Long employeeScheduleId) {
    employeeScheduleRepository.deleteById(employeeScheduleId); // 가게 스케줄은 완전 삭제하도록 수정
  }
}

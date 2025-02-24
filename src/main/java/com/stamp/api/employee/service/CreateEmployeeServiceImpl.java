package com.stamp.api.employee.service;

import com.stamp.api.employee.dto.request.CreateEmployeeReq;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import com.stamp.api.employeeschedule.repository.EmployeeScheduleRepository;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.exception.StoreErrorCode;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.global.exception.DomainException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateEmployeeServiceImpl implements CreateEmployeeService {

  private final EmployeeRepository employeeRepository;
  private final StoreRepository storeRepository;
  private final EmployeeScheduleRepository employeeScheduleRepository;

  @Transactional
  @Override
  public void enrollEmployee(Long storeId, CreateEmployeeReq createEmployeeReq) {
    Store store = findStoreWithSchedule(storeId);
    Employee employee = employeeRepository.save(Employee.of(createEmployeeReq, store));
    createEmployeeReq
        .scheduleList()
        .forEach(
            employeeScheduleReq ->
                employeeScheduleRepository.save(
                    EmployeeSchedule.of(employeeScheduleReq, employee)));
  }

  private Store findStoreWithSchedule(Long storeId) {
    return storeRepository
        .findByIdWithSchedules(storeId)
        .orElseThrow(
            () ->
                new DomainException(
                    StoreErrorCode.STORE_NOT_FOUNDED, "CreateEmployeeServiceImpl.enrollEmployee"));
  }
}

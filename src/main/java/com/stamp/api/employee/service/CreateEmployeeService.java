package com.stamp.api.employee.service;

import com.stamp.api.employee.dto.request.CreateEmployeeReq;

public interface CreateEmployeeService {
  void enrollEmployee(Long storeId, CreateEmployeeReq createEmployeeReq);
}

package com.stamp.api.employee.service;

import com.stamp.api.employee.dto.request.UpdateEmployeeReq;
import com.stamp.api.employee.dto.response.ReadEmployeeRes;

public interface UpdateEmployeeService {
  ReadEmployeeRes updateEmployee(Long employeeId, UpdateEmployeeReq updateEmployeeReq);
}

package com.stamp.api.auth.employer.service;

import com.stamp.api.auth.employer.dto.request.LoginEmployeeReq;
import com.stamp.api.auth.employer.dto.response.EmployeeLoginRes;
import com.stamp.api.auth.employer.dto.response.LoginRes;
import com.stamp.api.auth.exception.AuthErrorCode;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.global.exception.DomainException;
import com.stamp.global.jwt.JwtResponse;
import com.stamp.global.jwt.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthEmployeeServiceImpl implements AuthEmployeeService {

  private final EmployeeRepository employeeRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public EmployeeLoginRes login(LoginEmployeeReq req) {
    Employee employee =
        employeeRepository
            .findByContact(req.contact())
            .orElseThrow(
                () ->
                    new DomainException(
                        AuthErrorCode.EMPLOYEE_USER_NOT_FOUNDED, "AuthEmployeeServiceImpl.login"));
    JwtResponse response = jwtTokenProvider.generateToken(employee);
    return EmployeeLoginRes.of(response.token(), response.expiration(), employee.getStore().getId());
  }
}

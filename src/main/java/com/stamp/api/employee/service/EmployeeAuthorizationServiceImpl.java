package com.stamp.api.employee.service;

import com.stamp.api.attendance.exception.AttendanceErrorCode;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.exception.EmployeeErrorCode;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeAuthorizationServiceImpl implements EmployeeAuthorizationService {

    private final EmployeeRepository employeeRepository;

    /**
     * Public Method
     * 로그인 한 유저의 타입이 Employee가 맞는지 체크
     * 아니라면 throw Exception
     */
    @Override
    public void isEmployee(UserDetails user) {

        /*
           User가 Employee가 맞는지 체크   -> UserDetail을 구현해야함
        */
    }

    /**
     * Public Method
     * 1. 로그인 한 유저가 Employee 타입이 맞는지 체크
     * 2. 유저가 storeId의 store에 소속되어있는지 체크
     * 아니라면 throw Exception
     */
    @Override
    public void checkEmployeeStoreAuthority(Long storeId, UserDetails userDetails) {

        /*
           User가 Employee가 맞는지 체크   -> UserDetail을 구현해야함
        */

        Employee employee =
                employeeRepository
                        .findById(Long.parseLong(userDetails.getUsername()))
                        .orElseThrow(
                                () ->
                                        new DomainException(
                                                EmployeeErrorCode.EMPLOYEE_NO_AUTHORITY_TO_STORE_ERROR,
                                                "EmployeeAuthorizationServiceImpl.checkEmployeeStoreAuthority"));

        if (!employee.getStore().getId().equals(storeId))
            throw new DomainException(
                    EmployeeErrorCode.EMPLOYEE_NO_AUTHORITY_TO_STORE_ERROR,
                    "EmployeeAuthorizationServiceImpl.checkEmployeeStoreAuthority");
    }
}

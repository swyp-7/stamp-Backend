package com.stamp.api.employee.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface EmployeeAuthorizationService {

    void isEmployee(UserDetails user);

    void checkEmployeeStoreAuthority(Long storeId, UserDetails userDetails);
}

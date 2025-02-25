package com.stamp.api.employeruser.service;

import com.stamp.api.employeruser.dto.response.EmployerUserRes;

public interface ReadEmployerUserService {
  EmployerUserRes getUserWithStore(Long userId);
}

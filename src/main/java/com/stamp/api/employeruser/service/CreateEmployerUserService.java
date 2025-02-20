package com.stamp.api.employeruser.service;

import com.stamp.api.employeruser.dto.CreateEmployerUserReq;
import com.stamp.api.employeruser.dto.CreateSocialEmployerUserReq;
import com.stamp.api.employeruser.entity.EmployerUser;

public interface CreateEmployerUserService {
  EmployerUser createEmployerUser(CreateEmployerUserReq createEmployerUserReq);

  EmployerUser createSocialEmployerUser(
      CreateSocialEmployerUserReq req, EmployerUser oauthEmployerUser);
}

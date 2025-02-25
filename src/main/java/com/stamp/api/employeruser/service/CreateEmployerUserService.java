package com.stamp.api.employeruser.service;

import com.stamp.api.employeruser.dto.request.CreateEmployerUserReq;
import com.stamp.api.employeruser.dto.request.CreateSocialEmployerUserReq;
import com.stamp.api.employeruser.entity.EmployerUser;

public interface CreateEmployerUserService {
  EmployerUser createEmployerUser(CreateEmployerUserReq createEmployerUserReq);

  EmployerUser createSocialEmployerUser(
      CreateSocialEmployerUserReq req, EmployerUser oauthEmployerUser);
}

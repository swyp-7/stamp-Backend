package com.stamp.api.employeruser.dto.response;

import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.store.dto.response.ReadStoreRes;

public record EmployerUserRes(
    Long id, String name, String email, String contact, ReadStoreRes store) {
  public static EmployerUserRes of(EmployerUser employerUser, ReadStoreRes store) {
    return new EmployerUserRes(
        employerUser.getId(),
        employerUser.getName(),
        employerUser.getEmail(),
        employerUser.getContact(),
        store);
  }
}

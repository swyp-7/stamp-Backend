package com.stamp.api.extraShift.service;

import com.stamp.api.extraShift.dto.request.ExtraShiftReq;

public interface CreateExtraShiftService {
  void createExtraShiftRequest(Long storeId, Long employeeId, ExtraShiftReq extraShiftReq);
}

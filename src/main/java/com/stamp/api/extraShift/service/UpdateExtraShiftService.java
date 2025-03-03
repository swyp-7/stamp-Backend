package com.stamp.api.extraShift.service;

import com.stamp.api.extraShift.dto.response.ExtraShiftRes;

public interface UpdateExtraShiftService {
  ExtraShiftRes acceptExtraShiftRequest(Long extraShiftId, Long employeeId);

  ExtraShiftRes rejectExtraShiftRequest(Long extraShiftId, Long employeeId);
}

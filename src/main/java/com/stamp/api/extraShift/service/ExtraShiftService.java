package com.stamp.api.extraShift.service;

import com.stamp.api.extraShift.dto.request.ExtraShiftReq;
import com.stamp.api.extraShift.dto.response.ExtraShiftRes;
import java.util.List;

public interface ExtraShiftService {
  void createExtraShiftRequest(Long storeId, Long employeeId, ExtraShiftReq extraShiftReq);

  List<ExtraShiftRes> getExtraShiftRequests(Long aLong);

  ExtraShiftRes acceptExtraShiftRequest(Long extraShiftId, Long employeeId);

  ExtraShiftRes rejectExtraShiftRequest(Long extraShiftId, Long employeeId);
}

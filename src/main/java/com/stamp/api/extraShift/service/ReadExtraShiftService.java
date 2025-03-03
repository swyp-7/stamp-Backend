package com.stamp.api.extraShift.service;

import com.stamp.api.extraShift.dto.response.ExtraShiftRes;
import java.util.List;

public interface ReadExtraShiftService {
  List<ExtraShiftRes> getExtraShiftRequests(Long employeeId);
}

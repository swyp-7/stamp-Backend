package com.stamp.api.extraShift.dto.response;

import com.stamp.api.extraShift.entity.ExtraShift;
import com.stamp.api.extraShift.entity.RequestStatus;
import java.time.LocalDate;

public record ExtraShiftRes(Long id, LocalDate requestDate, RequestStatus status) {
  public static ExtraShiftRes of(ExtraShift extraShift) {
    return new ExtraShiftRes(
        extraShift.getId(), extraShift.getRequestDate(), extraShift.getStatus());
  }
}

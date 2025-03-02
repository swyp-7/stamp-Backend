package com.stamp.api.extraShift.dto.response;

import com.stamp.api.extraShift.entity.ExtraShift;
import java.time.LocalDate;

public record ExtraShiftRes(Long id, LocalDate requestDate, boolean isAccepted) {
  public static ExtraShiftRes of(ExtraShift extraShift) {
    return new ExtraShiftRes(
        extraShift.getId(), extraShift.getRequestDate(), extraShift.isAccepted());
  }
}

package com.stamp.api.employeeschedule.dto.request;

import com.stamp.api.common.WeekDay;
import java.time.LocalTime;

public record CreateEmployeeScheduleReq(
    WeekDay weekDay, LocalTime startTime, LocalTime endTime, boolean isAdditional) {

  public static CreateEmployeeScheduleReq of(
      WeekDay weekDay, LocalTime startTime, LocalTime endTime, boolean isAdditional) {
    return new CreateEmployeeScheduleReq(weekDay, startTime, endTime, isAdditional);
  }
}

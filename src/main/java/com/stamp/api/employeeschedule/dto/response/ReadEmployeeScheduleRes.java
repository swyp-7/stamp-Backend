package com.stamp.api.employeeschedule.dto.response;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import java.time.LocalTime;

public record ReadEmployeeScheduleRes(
    WeekDay weekDay, LocalTime startTime, LocalTime endTime, boolean isAdditional) {
  public static ReadEmployeeScheduleRes of(EmployeeSchedule employeeSchedule) {
    return new ReadEmployeeScheduleRes(
        employeeSchedule.getWeekDay(),
        employeeSchedule.getStartTime(),
        employeeSchedule.getEndTime(),
        employeeSchedule.isAdditional());
  }
}

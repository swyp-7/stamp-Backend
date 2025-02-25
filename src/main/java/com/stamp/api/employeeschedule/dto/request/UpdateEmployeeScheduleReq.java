package com.stamp.api.employeeschedule.dto.request;

import com.stamp.api.common.WeekDay;
import java.time.LocalTime;

public record UpdateEmployeeScheduleReq(
    Long id, WeekDay weekDay, LocalTime startTime, LocalTime endTime, boolean isAdditional) {}

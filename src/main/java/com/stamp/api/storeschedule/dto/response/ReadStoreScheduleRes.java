package com.stamp.api.storeschedule.dto.response;

import com.stamp.api.storeschedule.WeekDay;
import com.stamp.api.storeschedule.entity.StoreSchedule;
import java.time.LocalTime;

public record ReadStoreScheduleRes(
    WeekDay weekDay, LocalTime startTime, LocalTime endTime, boolean isClosed) {
  public static ReadStoreScheduleRes toDto(StoreSchedule storeSchedule) {
    return new ReadStoreScheduleRes(
        storeSchedule.getWeekDay(),
        storeSchedule.getStartTime(),
        storeSchedule.getEndTime(),
        storeSchedule.isClosed());
  }
}

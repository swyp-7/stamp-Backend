package com.stamp.api.storeschedule.dto.response;

import com.stamp.api.common.WeekDay;
import com.stamp.api.storeschedule.entity.StoreSchedule;
import java.time.LocalTime;

public record ReadStoreScheduleRes(
    Long id, WeekDay weekDay, LocalTime startTime, LocalTime endTime, boolean isClosed) {
  public static ReadStoreScheduleRes of(StoreSchedule storeSchedule) {
    return new ReadStoreScheduleRes(
        storeSchedule.getId(),
        storeSchedule.getWeekDay(),
        storeSchedule.getStartTime(),
        storeSchedule.getEndTime(),
        storeSchedule.isClosed());
  }
}

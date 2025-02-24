package com.stamp.api.storeschedule.dto.request;

import com.stamp.api.storeschedule.WeekDay;
import java.time.LocalTime;

public record UpdateStoreScheduleReq(
    Long id, // 스케줄이 최초로 생성 되는지, 혹은 기존에 존재하는 스케줄을 수정하는 것인지를 구분하기 위한 식별자. null일 경우 최초 생성으로 가정
    WeekDay weekDay,
    LocalTime startTime,
    LocalTime endTime,
    boolean isClosed) {}

package com.stamp.api.store.dto.response;

import java.time.Duration;

public record WageRes(
    Long employeeId,
    String name,
    Duration worktime,
    Integer pay,
    Integer totalWage,
    boolean isPaid) {}

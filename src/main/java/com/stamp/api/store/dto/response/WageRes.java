package com.stamp.api.store.dto.response;

import java.time.Duration;

public record WageRes(
    Long employeeId, String name, String worktime, Integer pay, Integer totalWage, boolean isPaid) {

  public static WageRes of(
      Long employeeId,
      String name,
      Duration worktime,
      Integer pay,
      Integer totalWage,
      boolean isPaid) {
    return new WageRes(
        employeeId,
        name,
        String.format(
            "%d:%02d:%02d", worktime.toHours(), worktime.toMillisPart(), worktime.toSecondsPart()),
        pay,
        totalWage,
        isPaid);
  }
}

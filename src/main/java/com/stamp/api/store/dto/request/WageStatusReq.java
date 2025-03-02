package com.stamp.api.store.dto.request;

import java.time.LocalDate;

public record WageStatusReq(
        String employeeId,
        boolean isPaid
) {
}

package com.stamp.api.employee.dto.request;

import com.stamp.api.employeeschedule.dto.request.UpdateEmployeeScheduleReq;
import java.time.LocalDate;
import java.util.List;

public record UpdateEmployeeReq(
    String name,
    LocalDate birthDate,
    String contact,
    String addressCommon,
    String addressDetail,
    LocalDate startDate,
    String bank,
    String bankAccountNumber,
    String wage,
    List<UpdateEmployeeScheduleReq> scheduleList) {}

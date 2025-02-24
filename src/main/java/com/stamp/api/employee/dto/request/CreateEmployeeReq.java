package com.stamp.api.employee.dto.request;

import com.stamp.api.employeeschedule.dto.request.CreateEmployeeScheduleReq;
import java.time.LocalDate;
import java.util.List;

public record CreateEmployeeReq(
    String name,
    LocalDate birthDate,
    String contact,
    String addressCommon,
    String addressDetail,
    LocalDate startDate,
    String bank,
    String bankAccountNumber,
    String wage,
    List<CreateEmployeeScheduleReq> scheduleList) {}

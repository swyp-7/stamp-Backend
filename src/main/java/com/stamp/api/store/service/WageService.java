package com.stamp.api.store.service;

import com.stamp.api.store.dto.request.WageStatusReq;
import com.stamp.api.store.dto.response.WageRes;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.List;

public interface WageService {

    /******************
     * 리펙토링 예정
     ******************/
    List<WageRes> getEmployeeWage(LocalDate firstDate, UserDetails userDetails);

    Void updateEmplyeeWageStatus(WageStatusReq wageStatusReq, LocalDate firstDate);
}

package com.stamp.api.employee.service;

import com.stamp.api.attendance.dto.response.AttendanceRes;
import com.stamp.api.attendance.repository.AttendanceRepository;
import com.stamp.api.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeAuthorizationService employeeAuthorizationService;
    private final EmployeeAttendanceService employeeAttendanceService;

    /**
     * Public Method
     * 유저 타입 검사 후  직원의 한달 출/퇴근 기록 조회
     */
    @Override
    public List<AttendanceRes> getAttendancesForMonth(LocalDate firstDate, UserDetails userDetails) {

        //권한 검사
        employeeAuthorizationService.isEmployee(userDetails);

        return employeeAttendanceService.getAttendancesForMonth(firstDate, userDetails);
    }

    /**
     * Public Method
     * 유저 타입 검사 후 직원의 일일 출/퇴근 기록 조회
     */
    @Override
    public List<AttendanceRes> getAttendancesForDay(LocalDate firstDate, UserDetails userDetails) {

        //권한 검사
        employeeAuthorizationService.isEmployee(userDetails);

        return employeeAttendanceService.getAttendancesForDay(firstDate, userDetails);
    }
}

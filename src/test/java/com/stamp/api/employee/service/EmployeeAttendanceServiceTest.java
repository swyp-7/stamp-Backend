package com.stamp.api.employee.service;

import com.stamp.api.attendance.repository.AttendanceRepository;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.store.entity.Store;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

public class EmployeeAttendanceServiceTest {

    private EmployeeAttendanceService employeeAttendanceService;

    final AttendanceRepository attendanceRepository = mock(AttendanceRepository.class);
    final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

    @Before
    public void setUp(){
        employeeAttendanceService =
                new EmployeeAttendanceServiceImpl(
                        attendanceRepository,
                        employeeRepository);
    }

    @DisplayName("출/퇴근 기록 조회 - 쿼리 id값 유효성 테스트")
    @Test
    public void getAttendancesForMonth_Success_QueryId_Test() {
        /*
            테스트 내용
            1. 쿼리 id값의 형태가 정상적인지
            2. 쿼리 id값 간의 차이가 1개월 만큼 나는지
         */

        // given
        LocalDate firstDate = LocalDate.of(2024, 7, 15);

        /* Store 준비*/
        Long storeId = 10525L;
        Store store = new Store();
        store.setId(storeId);

        /* Employee 준비 */
        Long employeeId = 623610525L;
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(employeeId);
        when(employee.getStore()).thenReturn(store);

        /* UserDetail 준비 */
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(String.valueOf(employeeId));

        /* employeeRepository 준비 */
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // when
        employeeAttendanceService.getAttendancesForMonth(firstDate, userDetails);
        ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
        verify(attendanceRepository, atLeastOnce())
                .findAttendancesByIdRangeAndEmployeeId(captor1.capture(), captor2.capture(), anyLong());
        // then
        /* 쿼리 id값의 앞이 storeId를 포함하고 있는지 */
        assertEquals("쿼리 Id값의 앞에 storeId값이 없음",
                String.format("%019d", storeId), captor1.getValue().substring(0, 19));

        /* 월 일의 자리수를 제외한 Id값은 동일 */
        assertEquals("쿼리 Id값이 불일치",
                captor2.getValue().substring(0, 24), captor1.getValue().substring(0, 24));
        assertEquals("쿼리 Id값이 불일치",
                captor2.getValue().substring(25), captor1.getValue().substring(25));

        /* 월의 일의 자리수만 1 올라감 */
        assertEquals("쿼리 Id값의 월 차이가 1이 아님",
                String.valueOf(captor2.getValue().charAt(24)),
                String.valueOf((char) (captor1.getValue().charAt(24) + 1)));

    }

    @DisplayName("출/퇴근 기록 조회 - 쿼리 id값 유효성 테스트")
    @Test
    public void getAttendancesForDay_Success_QueryId_Test() {

                /*
            테스트 내용
            1. 쿼리 id값의 형태가 정상적인지
            2. 쿼리 id값 간의 차이가 1일 만큼 나는지
         */

        // given
        LocalDate firstDate = LocalDate.of(2024, 7, 15);

        /* Store 준비*/
        Long storeId = 10525L;
        Store store = new Store();
        store.setId(storeId);

        /* Employee 준비 */
        Long employeeId = 623610525L;
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(employeeId);
        when(employee.getStore()).thenReturn(store);

        /* UserDetail 준비 */
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(String.valueOf(employeeId));

        /* employeeRepository 준비 */
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // when
        employeeAttendanceService.getAttendancesForDay(firstDate, userDetails);
        ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
                        verify(attendanceRepository, atLeastOnce())
                                .findAttendancesByIdRangeAndEmployeeId(captor1.capture(), captor2.capture(), anyLong());
        // then
        /* 쿼리 id값의 앞이 storeId를 포함하고 있는지 */
        assertEquals("쿼리 Id값의 앞에 storeId값이 없음",
                String.format("%019d", storeId), captor1.getValue().substring(0, 19));

        /* Day 일의 자리수를 제외한 Id값은 동일 */
        assertEquals("쿼리 Id값이 불일치",
                captor2.getValue().substring(0, 26), captor1.getValue().substring(0, 26));
        assertEquals("쿼리 Id값이 불일치",
                captor2.getValue().substring(27), captor1.getValue().substring(27));

        /* Day의 일의 자리수만 1 올라감 */
        assertEquals("쿼리 Id값의 월 차이가 1이 아님",
                String.valueOf(captor2.getValue().charAt(26)),
                String.valueOf((char) (captor1.getValue().charAt(26) + 1)));
    }
}
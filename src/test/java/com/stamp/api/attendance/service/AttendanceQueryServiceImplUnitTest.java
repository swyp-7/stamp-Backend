package com.stamp.api.attendance.service;

import com.stamp.api.attendance.repository.AttendanceRepository;
import com.stamp.api.attendance.repository.QRAuthCodeRepository;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.employeruser.repository.EmployerUserRepository;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.repository.StoreRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AttendanceQueryServiceImplUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.LENIENT);

    private AttendanceServiceImpl attendanceService;

    // Repository 등등은 Mock
    private StoreRepository storeRepository = mock(StoreRepository.class);
    private EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private EmployerUserRepository employerUserRepository = mock(EmployerUserRepository.class);
    private AttendanceRepository attendanceRepository = mock(AttendanceRepository.class);
    private QRAuthCodeRepository qrAuthCodeRepository = mock(QRAuthCodeRepository.class);

    // QRManageServiceImpl 실제 구현체
    private QRManageService qrManageService = new QRManageServiceImpl(qrAuthCodeRepository);

    @Before
    public void setUp() {
        attendanceService =
                new AttendanceServiceImpl(
                        qrManageService,
                        storeRepository,
                        employerUserRepository,
                        employeeRepository,
                        attendanceRepository);
    }

    @DisplayName("한달 출/퇴근 로그 조회시 Id값의 범위 유효성 테스트")
    @Test
    public void getAttendancesForMonth_IdRange_Check(){

        //given
        Long storeId = 10525L;
        Long employerUserId = 5915L;
        LocalDate firstDate = LocalDate.of(2024, 7, 1);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(String.valueOf(employerUserId));

        EmployerUser employerUser = mock(EmployerUser.class);
        when(employerUser.getId()).thenReturn(employerUserId);

        Store store = new Store();
        store.setId(storeId);
        store.setEmployerUser(employerUser);

        when(storeRepository.findById(storeId)).thenReturn(java.util.Optional.of(store));

        //when
        attendanceService.getAttendancesForMonth(storeId, firstDate, userDetails);
        ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
        verify(attendanceRepository, atLeastOnce()).findAttendancesByIdRange(captor1.capture(), captor2.capture());


        //then
        /* 월 앞의 Id값은 동일 */
        assertEquals(
                captor2.getValue().substring(0,24),
                captor1.getValue().substring(0,24));

        /* 월의 일의 자리수만 1 올라감 */
        assertEquals(
                String.valueOf(captor2.getValue().charAt(24)),
                String.valueOf((char)(captor1.getValue().charAt(24)+1)));

        /* 월 뒤의 Id값도 동일 */
        assertEquals(
                captor2.getValue().substring(25),
                captor1.getValue().substring(25));
    }

    @DisplayName("하루 출/퇴근 로그 조회시 Id값의 범위 유효성 테스트")
    @Test
    public void getAttendancesForDay_IdRange_Check(){

        //given
        Long storeId = 10525L;
        Long employerUserId = 5915L;
        LocalDate firstDate = LocalDate.of(2024, 7, 15);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(String.valueOf(employerUserId));

        EmployerUser employerUser = mock(EmployerUser.class);
        when(employerUser.getId()).thenReturn(employerUserId);

        Store store = new Store();
        store.setId(storeId);
        store.setEmployerUser(employerUser);

        when(storeRepository.findById(storeId)).thenReturn(java.util.Optional.of(store));

        //when
        attendanceService.getAttendancesForDay(storeId, firstDate, userDetails);
        ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
        verify(attendanceRepository, atLeastOnce()).findAttendancesByIdRange(captor1.capture(), captor2.capture());


        //then
        /* 월 앞의 Id값은 동일 */
        assertEquals(
                captor2.getValue().substring(0,26),
                captor1.getValue().substring(0,26));

        /* 월의 일의 자리수만 1 올라감 */
        assertEquals(
                String.valueOf(captor2.getValue().charAt(26)),
                String.valueOf((char)(captor1.getValue().charAt(26)+1)));
    }
}

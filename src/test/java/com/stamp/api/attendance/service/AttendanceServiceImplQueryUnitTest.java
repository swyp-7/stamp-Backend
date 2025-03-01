package com.stamp.api.attendance.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.stamp.api.attendance.dto.requeset.AttendanceReq;
import com.stamp.api.attendance.dto.requeset.AttendanceUpdateReq;
import com.stamp.api.attendance.dto.response.AttendanceRes;
import com.stamp.api.attendance.entity.Attendance;
import com.stamp.api.attendance.entity.AttendanceEnum;
import com.stamp.api.attendance.repository.AttendanceRepository;
import com.stamp.api.attendance.repository.QRAuthCodeRepository;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.employeruser.repository.EmployerUserRepository;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.repository.StoreRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UserDetails;

public class AttendanceServiceImplQueryUnitTest {

  @Rule public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.LENIENT);

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
  public void getAttendancesForMonth_IdRange_Check() {

    // given
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

    // when
    attendanceService.getAttendancesForMonth(storeId, firstDate, userDetails);
    ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
    verify(attendanceRepository, atLeastOnce())
        .findAttendancesByIdRange(captor1.capture(), captor2.capture());

    // then
    /* 월 앞의 Id값은 동일 */
    assertEquals(captor2.getValue().substring(0, 24), captor1.getValue().substring(0, 24));

    /* 월의 일의 자리수만 1 올라감 */
    assertEquals(
        String.valueOf(captor2.getValue().charAt(24)),
        String.valueOf((char) (captor1.getValue().charAt(24) + 1)));

    /* 월 뒤의 Id값도 동일 */
    assertEquals(captor2.getValue().substring(25), captor1.getValue().substring(25));
  }

  @DisplayName("하루 출/퇴근 로그 조회시 Id값의 범위 유효성 테스트")
  @Test
  public void getAttendancesForDay_IdRange_Check() {

    // given
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

    // when
    attendanceService.getAttendancesForDay(storeId, firstDate, userDetails);
    ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
    verify(attendanceRepository, atLeastOnce())
        .findAttendancesByIdRange(captor1.capture(), captor2.capture());

    // then
    /* 월 앞의 Id값은 동일 */
    assertEquals(captor2.getValue().substring(0, 26), captor1.getValue().substring(0, 26));

    /* 월의 일의 자리수만 1 올라감 */
    assertEquals(
        String.valueOf(captor2.getValue().charAt(26)),
        String.valueOf((char) (captor1.getValue().charAt(26) + 1)));
  }

  @DisplayName("출/퇴근 로그 업데이트 - 신규 생성 성공 테스트")
  @Test
  public void updateAttendanc_Create_Success_Test(){

    // given
    /* EmployerUser, UserDetail 준비 */
    Long employerUserId = 5915L;
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn(String.valueOf(employerUserId));
    EmployerUser employerUser = mock(EmployerUser.class);
    when(employerUser.getId()).thenReturn(employerUserId);

    /* Store 준비*/
    Long storeId = 10525L;
    Store store = new Store();
    store.setId(storeId);
    store.setEmployerUser(employerUser);

    /* Employee 준비 */
    Long employeeId = 623610525L;
    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(employeeId);
    when(employee.getStore()).thenReturn(store);

    /* Employee 레포 준비 */
    when(employeeRepository.findById(employeeId)).thenReturn(java.util.Optional.of(employee));

    /* Store 레포 준비 */
    when(storeRepository.findById(storeId)).thenReturn(java.util.Optional.of(store));

    /* AttendanceUpdateReq 준비 */
    LocalDate date = LocalDate.of(2024, 5, 5);
    LocalTime time = LocalTime.of(14, 50, 0);
    AttendanceUpdateReq req = new AttendanceUpdateReq(employeeId, AttendanceEnum.PUNCH_IN, date, time);


    // when
    attendanceService.updateAttendance(storeId, req, userDetails);
    ArgumentCaptor<Attendance> captor = ArgumentCaptor.forClass(Attendance.class);
    verify(attendanceRepository, atLeastOnce())
            .save(captor.capture());

    //then
    Attendance attendance = captor.getValue();
    assertEquals(employeeId, attendance.getEmployeeId());
    assertEquals(date,attendance.getDate());
    assertEquals(time,attendance.getTime());
  }

  @DisplayName("출/퇴근 로그 업데이트 - 기존 로그 변경 성공 테스트")
  @Test
  public void updateAttendance_Modify_Success_Test(){

    // given
    /* EmployerUser, UserDetail 준비 */
    Long employerUserId = 5915L;
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn(String.valueOf(employerUserId));
    EmployerUser employerUser = mock(EmployerUser.class);
    when(employerUser.getId()).thenReturn(employerUserId);

    /* Store 준비*/
    Long storeId = 10525L;
    Store store = new Store();
    store.setId(storeId);
    store.setEmployerUser(employerUser);

    /* Employee 준비 */
    Long employeeId = 623610525L;
    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(employeeId);
    when(employee.getStore()).thenReturn(store);

    /* Employee 레포 준비 */
    when(employeeRepository.findById(employeeId)).thenReturn(java.util.Optional.of(employee));

    /* Store 레포 준비 */
    when(storeRepository.findById(storeId)).thenReturn(java.util.Optional.of(store));

    /* AttendanceUpdateReq 준비 */
    LocalDate reqDate = LocalDate.of(2024, 5, 5);
    LocalTime reqTime = LocalTime.of(14, 50, 0);
    AttendanceEnum attendanceEnum = AttendanceEnum.PUNCH_IN;
    AttendanceUpdateReq req = new AttendanceUpdateReq(employeeId, attendanceEnum, reqDate, reqTime);

    /* 기존 Attendance 준비 */
    LocalTime defaultTime = LocalTime.of(13, 53, 22);
    Attendance attendance = Attendance.of(storeId, employeeId, attendanceEnum, LocalDateTime.of(reqDate, defaultTime));

    /* AttendanceRes 준비 */
    AttendanceRes attendanceRes = new AttendanceRes(attendance.getId(), employee.getName(), attendance.getEmployeeId(), attendance.getAttendance(), attendance.getDate(), attendance.getTime());
    List<AttendanceRes> attendanceResList = Arrays.asList(attendanceRes);

    /* AttendanceRepository 준비 */
    String id1 = String.format("%019d", storeId) +
            reqDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
            (Integer)attendanceEnum.ordinal();
    String id2 = String.format("%019d", storeId) +
            reqDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
            ((Integer)attendanceEnum.ordinal() + 1);
    when(attendanceRepository.findAttendancesByIdRangeAndEmployeeId(id1, id2, employeeId))
            .thenReturn(attendanceResList);
    when(attendanceRepository.findById(attendance.getId()))
            .thenReturn(java.util.Optional.of(attendance));
    when(attendanceRepository.existsById(attendanceResList.getFirst().id()))
            .thenReturn(true);



    // when
    attendanceService.updateAttendance(storeId, req, userDetails);
    ArgumentCaptor<Attendance> captor = ArgumentCaptor.forClass(Attendance.class);
    verify(attendanceRepository, atLeastOnce()).findById(attendanceResList.getFirst().id());
    verify(attendanceRepository, atLeastOnce())
            .save(captor.capture());

    //then
    Attendance captoredAttendance = captor.getValue();
    assertEquals(employeeId, captoredAttendance.getEmployeeId());
    assertEquals(reqDate,captoredAttendance.getDate());
    assertEquals(reqTime,captoredAttendance.getTime());
  }
}

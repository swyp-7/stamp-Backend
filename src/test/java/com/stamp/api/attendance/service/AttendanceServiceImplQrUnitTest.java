package com.stamp.api.attendance.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.stamp.api.attendance.dto.response.QRCodeRes;
import com.stamp.api.attendance.repository.AttendanceRepository;
import com.stamp.api.attendance.repository.QRAuthCodeRepository;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.employeruser.repository.EmployerUserRepository;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.global.exception.DomainException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UserDetails;

public class AttendanceServiceImplQrUnitTest {
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

  @DisplayName("QR코드 생성, 조회 시 권한 검증 테스트")
  @Test
  public void createQR_Authority_Success_Test() {

    // given
    Long storeId = 10525L;
    Long employerUserId = 5915L;

    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn(String.valueOf(employerUserId));

    EmployerUser employerUser = mock(EmployerUser.class);
    when(employerUser.getId()).thenReturn(employerUserId);

    Store store = new Store();
    store.setId(storeId);
    store.setEmployerUser(employerUser);

    when(storeRepository.findById(storeId)).thenReturn(java.util.Optional.of(store));

    // when
    QRCodeRes qrCode = attendanceService.createQR(storeId, userDetails);

    // then
    assertNotNull(qrCode);
    assertNotNull(qrCode.byteArr());
    assertFalse(qrCode.byteArr().isEmpty());
  }

  @DisplayName("QR코드 생성, 조회시 권한 검증 실패 테스트")
  @Test
  public void createQR_Authority_Failure_Test() {

    // given
    Long storeId = 10525L;
    Long employerUserId = 5915L;
    Long fakeEmployerUserId = 999L;

    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn(String.valueOf(employerUserId));

    EmployerUser employerUser = mock(EmployerUser.class);
    when(employerUser.getId()).thenReturn(fakeEmployerUserId);

    Store store = new Store();
    store.setId(storeId);
    store.setEmployerUser(employerUser);

    when(storeRepository.findById(storeId)).thenReturn(java.util.Optional.of(store));

    try {

      // when
      QRCodeRes qrCode = attendanceService.createQR(storeId, userDetails);

      fail("예외가 발생하지 않음");
    } catch (DomainException e) {

      // then
      assertEquals(e.getError().getMessage(), "가게에 대한 엑세스 권한 없음.", e.getError().getMessage());
    }
  }

  @DisplayName("출퇴근 권한 성공 테스트")
  @Test
  public void punchIn_Authority_Success_Test() {

    // given
    Long storeId = 10525L;
    Long employeeId = 5915L;
    String authCode = "testAuthCode";

    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn(String.valueOf(employeeId));

    Store store = new Store();
    store.setId(storeId);

    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(employeeId);
    when(employee.getStore()).thenReturn(store);

    when(employeeRepository.findById(employeeId)).thenReturn(java.util.Optional.of(employee));

    try {

      // when
      attendanceService.punchIn(storeId, userDetails, authCode);

      fail("예외가 발생하지 않음");
    } catch (DomainException e) {

      // then
      /*권한 체크가 성공한다면 권한 체크 에러가 아닌 인증 코드 에러가 발생해야 함*/
      assertEquals(e.getError().getMessage(), "인증 코드 오류", e.getError().getMessage());
    }
  }

  @DisplayName("출퇴근 권한 실패 테스트")
  @Test
  public void punchIn_Authority_Fail_Test() {

    // given
    Long storeId = 10525L;
    Long fakeStoreId = 999L;
    Long employeeId = 5915L;
    String authCode = "testAuthCode";

    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn(String.valueOf(employeeId));

    Store store = new Store();
    store.setId(storeId);

    Employee employee = mock(Employee.class);
    when(employee.getId()).thenReturn(employeeId);
    when(employee.getStore()).thenReturn(store);

    when(employeeRepository.findById(employeeId)).thenReturn(java.util.Optional.of(employee));

    try {

      // when
      attendanceService.punchIn(fakeStoreId, userDetails, authCode);

      fail("예외가 발생하지 않음");
    } catch (DomainException e) {

      // then
      assertEquals(e.getError().getMessage(), "가게에 대한 엑세스 권한 없음.", e.getError().getMessage());
    }
  }
}

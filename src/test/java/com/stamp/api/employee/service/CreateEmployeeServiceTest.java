package com.stamp.api.employee.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.dto.request.CreateEmployeeReq;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeeschedule.dto.request.CreateEmployeeScheduleReq;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import com.stamp.api.employeeschedule.repository.EmployeeScheduleRepository;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.global.exception.DomainException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("직원 등록 비즈니스 로직 테스트")
@ExtendWith(MockitoExtension.class)
class CreateEmployeeServiceTest {

  @Mock private EmployeeRepository employeeRepository;
  @Mock private StoreRepository storeRepository;
  @Mock private EmployeeScheduleRepository employeeScheduleRepository;

  @InjectMocks private CreateEmployeeServiceImpl createEmployeeService;

  private static final Long STORE_ID = 1L;
  private static final Long EMPLOYEE_ID = 10L;

  private Store mockStore;
  private Employee mockEmployee;
  private CreateEmployeeReq mockRequest;

  @BeforeEach
  void setUp() {
    // 가게 및 직원 객체 생성
    mockStore = mock(Store.class);
    mockEmployee = mock(Employee.class);

    // 직원 스케줄 요청 생성
    CreateEmployeeScheduleReq scheduleReq =
        new CreateEmployeeScheduleReq(
            WeekDay.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), false);
    mockRequest =
        new CreateEmployeeReq(
            "김직원",
            LocalDate.of(2000, 1, 1),
            "010-1234-5678",
            "addressCommon",
            "addressDetail",
            LocalDate.of(2025, 1, 1),
            "테스트 은행",
            "테스트 계좌번호",
            "10000",
            List.of(scheduleReq));
  }

  @DisplayName("직원 정보와 함께 직원을 등록하면 성공한다.")
  @Test
  void givenEmployeeInfo_whenEnrollEmployee_thenReturnSuccess() {
    // given
    when(storeRepository.findByIdWithSchedules(STORE_ID)).thenReturn(Optional.of(mockStore));
    when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

    // when
    createEmployeeService.enrollEmployee(STORE_ID, mockRequest);

    // then
    verify(storeRepository, times(1)).findByIdWithSchedules(STORE_ID);
    verify(employeeRepository, times(1)).save(any(Employee.class));
    verify(employeeScheduleRepository, times(1)).save(any(EmployeeSchedule.class));
  }

  @DisplayName("존재하지 않는 가게에 직원을 등록하면 예외를 반환한다.")
  @Test
  void givenStoreNotFound_whenEnrollEmployee_thenThrowStoreNotFoundException() {
    // given
    when(storeRepository.findByIdWithSchedules(STORE_ID)).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> createEmployeeService.enrollEmployee(STORE_ID, mockRequest))
        .isInstanceOf(DomainException.class)
        .hasMessageContaining("CreateEmployeeServiceImpl.enrollEmployee");

    verify(storeRepository, times(1)).findByIdWithSchedules(STORE_ID);
    verify(employeeRepository, never()).save(any(Employee.class));
    verify(employeeScheduleRepository, never()).save(any(EmployeeSchedule.class));
  }
}

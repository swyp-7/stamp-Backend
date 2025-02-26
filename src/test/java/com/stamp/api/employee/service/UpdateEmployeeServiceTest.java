package com.stamp.api.employee.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.dto.request.UpdateEmployeeReq;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeeschedule.dto.request.UpdateEmployeeScheduleReq;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import com.stamp.api.employeeschedule.repository.EmployeeScheduleRepository;
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

@DisplayName("직원 수정 비즈니스 로직 테스트")
@ExtendWith(MockitoExtension.class)
class UpdateEmployeeServiceTest {

  @Mock private EmployeeRepository employeeRepository;
  @Mock private EmployeeScheduleRepository employeeScheduleRepository;

  @InjectMocks private UpdateEmployeeServiceImpl updateEmployeeService;

  private static final Long EMPLOYEE_ID = 10L;
  private static final Long SCHEDULE_ID = 100L;
  private Employee mockEmployee;
  private UpdateEmployeeReq mockRequest;

  @BeforeEach
  void setUp() {
    // 직원 객체 생성
    mockEmployee = mock(Employee.class);
    // 직원 스케줄 수정 요청 생성
    UpdateEmployeeScheduleReq mockScheduleReq =
        new UpdateEmployeeScheduleReq(
            SCHEDULE_ID, WeekDay.MONDAY, LocalTime.of(10, 0), LocalTime.of(19, 0), false);
    mockRequest =
        new UpdateEmployeeReq(
            "김직원",
            LocalDate.of(2000, 1, 1),
            "010-1234-5678",
            "addressCommon",
            "addressDetail",
            LocalDate.of(2025, 1, 1),
            "테스트 은행",
            "테스트 계좌번호",
            "10000",
            List.of(mockScheduleReq));
  }

  @DisplayName("직원 정보가 존재할 때, 정상적으로 수정된다.")
  @Test
  void givenEmployeeInfo_whenUpdateEmployee_thenReturnSuccess() {
    // given
    when(employeeRepository.findByIdWithSchedules(EMPLOYEE_ID))
        .thenReturn(Optional.of(mockEmployee));
    when(employeeScheduleRepository.findById(SCHEDULE_ID))
        .thenReturn(Optional.of(mock(EmployeeSchedule.class)));

    // when
    updateEmployeeService.updateEmployee(EMPLOYEE_ID, mockRequest);

    // then
    verify(mockEmployee, times(1)).update(mockRequest);
    verify(employeeScheduleRepository, times(1)).findById(SCHEDULE_ID);
  }

  @DisplayName("존재하지 않는 직원 정보를 수정하려고 하면 예외가 발생한다.")
  @Test
  void givenInvalidEmployeeId_whenUpdateEmployee_thenThrowException() {
    // given
    when(employeeRepository.findByIdWithSchedules(EMPLOYEE_ID)).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> updateEmployeeService.updateEmployee(EMPLOYEE_ID, mockRequest))
        .isInstanceOf(DomainException.class)
        .hasMessageContaining("UpdateEmployeeServiceImpl.updateEmployee");

    verify(employeeRepository, times(1)).findByIdWithSchedules(EMPLOYEE_ID);
  }

  @DisplayName("존재하지 않는 직원 스케줄을 수정하려고 하면 예외가 발생한다.")
  @Test
  void givenInvalidScheduleId_whenUpdateEmployee_thenThrowException() {
    // given
    when(employeeRepository.findByIdWithSchedules(EMPLOYEE_ID))
        .thenReturn(Optional.of(mockEmployee));
    when(employeeScheduleRepository.findById(SCHEDULE_ID)).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> updateEmployeeService.updateEmployee(EMPLOYEE_ID, mockRequest))
        .isInstanceOf(DomainException.class)
        .hasMessageContaining("UpdateEmployeeServiceImpl.updateEmployee");

    verify(employeeScheduleRepository, times(1)).findById(SCHEDULE_ID);
  }
}

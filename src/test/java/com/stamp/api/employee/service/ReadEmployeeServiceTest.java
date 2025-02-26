package com.stamp.api.employee.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.stamp.api.employee.dto.response.ReadEmployeeRes;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.global.exception.DomainException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("직원 조회 비즈니스 로직 테스트")
@ExtendWith(MockitoExtension.class)
class ReadEmployeeServiceTest {

  @Mock private EmployeeRepository employeeRepository;
  @Mock private StoreRepository storeRepository;

  @InjectMocks private ReadEmployeeServiceImpl readEmployeeService;

  private Store mockStore;
  private Employee mockEmployee1, mockEmployee2;

  @BeforeEach
  void setUp() {
    mockStore = mock(Store.class);
    mockEmployee1 = mock(Employee.class);
    mockEmployee2 = mock(Employee.class);
  }

  @Test
  @DisplayName("기간 내 근무하는 직원만 조회된다")
  void givenStoreIdAndDateRange_whenGetEmployeeByPeriod_thenReturnFilteredEmployees() {
    // given
    Long storeId = 1L;
    LocalDate startDate = LocalDate.of(2025, 2, 24); // 월요일
    LocalDate endDate = LocalDate.of(2025, 2, 26); // 수요일

    when(storeRepository.findByIdWithSchedules(storeId)).thenReturn(Optional.of(mockStore));
    when(employeeRepository.findActiveEmployeesByPeriod(eq(mockStore), eq(endDate), any()))
        .thenReturn(List.of(mockEmployee1)); // 특정 직원만 필터링됨

    // when
    List<ReadEmployeeRes> result =
        readEmployeeService.getEmployeeByPeriod(storeId, startDate, endDate);

    // then
    assertThat(result).hasSize(1);
    verify(employeeRepository, times(1))
        .findActiveEmployeesByPeriod(eq(mockStore), eq(endDate), any());
  }

  @Test
  @DisplayName("존재하지 않는 가게 ID로 조회하면 예외를 던진다")
  void givenInvalidStoreId_whenGetEmployeeByPeriod_thenThrowException() {
    // given
    Long invalidStoreId = 999L;
    LocalDate startDate = LocalDate.of(2025, 2, 24);
    LocalDate endDate = LocalDate.of(2025, 2, 26);

    when(storeRepository.findByIdWithSchedules(invalidStoreId)).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(
            () -> readEmployeeService.getEmployeeByPeriod(invalidStoreId, startDate, endDate))
        .isInstanceOf(DomainException.class)
        .hasMessageContaining("ReadEmployeeServiceImpl.getEmployee");
  }
}

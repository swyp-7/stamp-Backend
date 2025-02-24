package com.stamp.api.employee.repository;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.store.entity.Store;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  @Query(
      "SELECT e FROM Employee e LEFT JOIN FETCH e.employeeScheduleList WHERE e.id = :employeeId") // 직원 단일 조회
  Optional<Employee> findByIdWithSchedules(@Param("employeeId") Long employeeId);

  // 모든 직원 조회
  @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.employeeScheduleList WHERE e.store = :store")
  List<Employee> findAllByStoreWithSchedules(@Param("store") Store store);

  @Query(
      "SELECT DISTINCT e FROM Employee e "
          + "LEFT JOIN FETCH e.employeeScheduleList es "
          + "WHERE e.store = :store "
          + "AND e.endDate IS NULL "
          + "AND e.startDate <= :currentDate "
          + "AND EXISTS ("
          + "SELECT 1 FROM EmployeeSchedule s "
          + "WHERE s.employee = e "
          + "AND s.weekDay IN :weekDays"
          + ") "
          + "ORDER BY es.weekDay ASC")
  List<Employee> findActiveEmployeesByPeriod(
      @Param("store") Store store,
      @Param("currentDate") LocalDate currentDate,
      @Param("weekDays") List<WeekDay> weekDays);
}

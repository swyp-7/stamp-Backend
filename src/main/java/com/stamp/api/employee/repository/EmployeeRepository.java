package com.stamp.api.employee.repository;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.store.entity.Store;
import java.time.LocalDate;
import java.time.LocalTime;
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


  Optional<Employee> findByContact(String contact);

  @Query(
      "SELECT DISTINCT e FROM Employee e "
          + "LEFT JOIN FETCH e.employeeScheduleList es "
          + "WHERE e.store = :store "
          + "AND e.endDate IS NULL "
          + "AND e.startDate <= :date "
          + "AND EXISTS ("
          + "SELECT 1 FROM EmployeeSchedule s "
          + "WHERE s.employee = e "
          + "AND s.weekDay = :weekDay "
          + "AND ("
          + "    (s.startTime IS NULL AND s.endTime IS NULL) " // 시간이 null인 경우(추가 근무 작성한 인원)
          + "    OR "
          + "    (s.startTime <= :endTime AND s.endTime >= :startTime)" // 시간 범위가 겹치는 경우
          + ")"
          + ") "
          + "ORDER BY e.name ASC")
  List<Employee> findAvailableEmployeesForTimeSlot(
      @Param("store") Store store,
      @Param("date") LocalDate date,
      @Param("weekDay") WeekDay weekDay,
      @Param("startTime") LocalTime startTime,
      @Param("endTime") LocalTime endTime);
}

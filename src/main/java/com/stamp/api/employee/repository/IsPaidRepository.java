package com.stamp.api.employee.repository;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.entity.IsPaid;
import com.stamp.api.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IsPaidRepository extends JpaRepository<IsPaid, Long> {

  Optional<IsPaid> findByEmployeeAndDate(Employee employee, LocalDate date);
}

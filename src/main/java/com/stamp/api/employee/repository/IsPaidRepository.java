package com.stamp.api.employee.repository;

import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.entity.IsPaid;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IsPaidRepository extends JpaRepository<IsPaid, Long> {

  Optional<IsPaid> findByEmployeeAndDate(Employee employee, LocalDate date);
}

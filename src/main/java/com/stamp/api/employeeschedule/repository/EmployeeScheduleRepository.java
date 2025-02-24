package com.stamp.api.employeeschedule.repository;

import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule, Long> {}

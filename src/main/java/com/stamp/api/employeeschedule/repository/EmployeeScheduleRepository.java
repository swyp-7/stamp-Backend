package com.stamp.api.employeeschedule.repository;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule, Long> {

  List<EmployeeSchedule> findByEmployeeAndWeekDay(Employee employee, WeekDay weekDay);
}

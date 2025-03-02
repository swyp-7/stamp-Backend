package com.stamp.api.employee.entity;

import com.stamp.api.employee.dto.request.CreateEmployeeReq;
import com.stamp.api.employee.dto.request.UpdateEmployeeReq;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import com.stamp.api.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IsPaid {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  Employee employee;

  LocalDate date;

  @Setter
  boolean isPaid;

  public static IsPaid of(Employee employee, LocalDate date, boolean isPaid) {
    return new IsPaid(
            null,
            employee,
            LocalDate.of(date.getYear(), date.getMonth(), 1),
            isPaid);
  }
}

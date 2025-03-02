package com.stamp.api.employee.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

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

  @Setter boolean isPaid;

  public static IsPaid of(Employee employee, LocalDate date, boolean isPaid) {
    return new IsPaid(null, employee, LocalDate.of(date.getYear(), date.getMonth(), 1), isPaid);
  }
}

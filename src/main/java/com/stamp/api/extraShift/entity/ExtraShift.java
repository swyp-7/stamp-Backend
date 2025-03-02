package com.stamp.api.extraShift.entity;

import com.stamp.api.employee.entity.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExtraShift {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate requestDate; // 추가 근무 요청 날짜
  private boolean isAccepted; // true 수락, false 삭제

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  public static ExtraShift of(LocalDate requestDate, boolean isAccepted, Employee employee) {
    return new ExtraShift(null, requestDate, isAccepted, employee);
  }

  public void accept() {
    this.isAccepted = true;
  }

  public void reject() {
    this.isAccepted = false;
  }
}

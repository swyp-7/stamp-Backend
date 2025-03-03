package com.stamp.api.extraShift.entity;

import com.stamp.api.employee.entity.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

  @Enumerated(EnumType.STRING)
  private RequestStatus status = RequestStatus.REQUESTED;

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  public static ExtraShift of(LocalDate requestDate, RequestStatus status, Employee employee) {
    return new ExtraShift(null, requestDate, status, employee);
  }

  public void changeStatus(RequestStatus status) {
    this.status = status;
  }
}

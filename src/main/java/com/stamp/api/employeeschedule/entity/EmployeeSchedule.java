package com.stamp.api.employeeschedule.entity;

import com.stamp.api.common.WeekDay;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employeeschedule.dto.request.CreateEmployeeScheduleReq;
import com.stamp.api.employeeschedule.dto.request.UpdateEmployeeScheduleReq;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmployeeSchedule {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalTime startTime;
  private LocalTime endTime;
  private WeekDay weekDay;
  private boolean isAdditional; // 추가 근무 시간으로 등록한 것인지 아닌지를 구분

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  public static EmployeeSchedule of(CreateEmployeeScheduleReq createReq, Employee employee) {

    return new EmployeeSchedule(
        null,
        createReq.startTime(),
        createReq.endTime(),
        createReq.weekDay(),
        createReq.isAdditional(),
        employee,
        null,
        null,
        null);
  }

  public void update(UpdateEmployeeScheduleReq updateReq) {
    this.startTime = updateReq.startTime();
    this.endTime = updateReq.endTime();
    this.weekDay = updateReq.weekDay();
    this.isAdditional = updateReq.isAdditional();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }
}

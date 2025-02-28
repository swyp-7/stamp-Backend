package com.stamp.api.attendance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

  @Id private String id;

  private Long storeId;

  private Long employeeId;

  @Enumerated(EnumType.ORDINAL)
  private AttendanceEnum attendance;

  @Temporal(TemporalType.DATE)
  private LocalDate date;

  @Temporal(TemporalType.TIME)
  private LocalTime time;

  public static Attendance of(
      Long storeId, Long employeeId, AttendanceEnum attendance, LocalDateTime dateTime) {
    String id =
        String.format("%019d", storeId)
            + dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            + attendance.ordinal()
            + String.format("%019d", employeeId);

    return new Attendance(
        id, storeId, employeeId, attendance, dateTime.toLocalDate(), dateTime.toLocalTime());
  }
}

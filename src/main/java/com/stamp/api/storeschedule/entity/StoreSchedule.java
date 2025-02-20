package com.stamp.api.storeschedule.entity;

import com.stamp.api.store.entity.Store;
import com.stamp.api.storeschedule.WeekDay;
import com.stamp.api.storeschedule.dto.request.UpdateStoreScheduleReq;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class StoreSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalTime startTime;
  private LocalTime endTime;

  @Enumerated(EnumType.STRING)
  private WeekDay weekDay;

  private boolean isClosed;
  @ManyToOne private Store store;

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  public static StoreSchedule of(
      LocalTime startTime, LocalTime endTime, WeekDay weekDay, Store store, boolean isClosed) {
    return new StoreSchedule(null, startTime, endTime, weekDay, isClosed, store, null, null, null);
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  public void update(UpdateStoreScheduleReq req) {
    this.startTime = req.startTime();
    this.endTime = req.endTime();
    this.weekDay = req.weekDay();
    this.isClosed = req.isClosed();
  }
}

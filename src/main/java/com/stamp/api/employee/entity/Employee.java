package com.stamp.api.employee.entity;

import com.stamp.api.employee.dto.request.CreateEmployeeReq;
import com.stamp.api.employeeschedule.entity.EmployeeSchedule;
import com.stamp.api.store.entity.Store;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private LocalDate birthDate;
  private String contact;
  private String wage; // per hour
  private String addressCommon;
  private String addressDetail;
  private String bank;
  private String bankAccountNumber;
  private LocalDate startDate;
  private LocalDate endDate = null;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private Store store;

  @OneToMany(mappedBy = "employee")
  private List<EmployeeSchedule> employeeScheduleList = new ArrayList<>();

  public static Employee of(CreateEmployeeReq createEmployeeReq, Store store) {
    return new Employee(
        null,
        createEmployeeReq.name(),
        createEmployeeReq.birthDate(),
        createEmployeeReq.contact(),
        createEmployeeReq.wage(),
        createEmployeeReq.addressCommon(),
        createEmployeeReq.addressDetail(),
        createEmployeeReq.bank(),
        createEmployeeReq.bankAccountNumber(),
        createEmployeeReq.startDate(),
        null,
        store,
        new ArrayList<>());
  }

  public void update(
      String name,
      LocalDate birthDate,
      String contact,
      String wage,
      LocalDate startDate,
      Store store) {
    this.name = name;
    this.birthDate = birthDate;
    this.contact = contact;
    this.wage = wage;
    this.startDate = startDate;
    this.store = store;
  }

  public void terminate() {
    this.endDate = LocalDate.now();
  }
}

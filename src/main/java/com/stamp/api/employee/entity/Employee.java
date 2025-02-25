package com.stamp.api.employee.entity;

import com.stamp.api.employee.dto.request.CreateEmployeeReq;
import com.stamp.api.employee.dto.request.UpdateEmployeeReq;
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

  public static Employee of(CreateEmployeeReq createReq, Store store) {
    return new Employee(
        null,
        createReq.name(),
        createReq.birthDate(),
        createReq.contact(),
        createReq.wage(),
        createReq.addressCommon(),
        createReq.addressDetail(),
        createReq.bank(),
        createReq.bankAccountNumber(),
        createReq.startDate(),
        null,
        store,
        new ArrayList<>());
  }

  public void update(UpdateEmployeeReq updateReq) {
    this.name = updateReq.name();
    this.birthDate = updateReq.birthDate();
    this.contact = updateReq.contact();
    this.addressCommon = updateReq.addressCommon();
    this.addressDetail = updateReq.addressDetail();
    this.bank = updateReq.bank();
    this.bankAccountNumber = updateReq.bankAccountNumber();
    this.wage = updateReq.wage();
    this.startDate = updateReq.startDate();
  }

  public void terminate() {
    this.endDate = LocalDate.now();
  }
}

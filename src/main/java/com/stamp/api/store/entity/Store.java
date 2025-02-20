package com.stamp.api.store.entity;

import com.stamp.api.employeruser.entity.EmployerUser;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false, name = "employer_user_id")
  private EmployerUser employerUser;

  @Column(nullable = false, name = "business_number", unique = true, length = 12) // ***-**-***** 형식
  private String businessNumber;

  @Column(nullable = false, name = "name", length = 100)
  private String name;

  @Column(nullable = false, name = "address")
  private String address;

  private String businessType; // 업태, 사용자가 직접 입력

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  private LocalDateTime deletedAt; // deletedAt != null -> soft deleted

  private Store(
      EmployerUser employerUser,
      String businessNumber,
      String name,
      String address,
      String businessType) {
    this.employerUser = employerUser;
    this.businessNumber = businessNumber;
    this.name = name;
    this.address = address;
    this.businessType = businessType;
  }

  public static Store of(
      EmployerUser employerUser,
      String businessNumber,
      String name,
      String address,
      String businessType) {
    return new Store(employerUser, businessNumber, name, address, businessType);
  }

  public static Store delete(Store store) {
    store.deletedAt = LocalDateTime.now();
    return store;
  }
}

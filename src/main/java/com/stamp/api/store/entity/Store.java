package com.stamp.api.store.entity;

import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.store.dto.request.CreateStoreReq;
import com.stamp.api.store.dto.request.UpdateStoreReq;
import com.stamp.api.storeschedule.entity.StoreSchedule;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

  @Column(nullable = false, name = "address_common")
  private String addressCommon;

  @Column(nullable = false, name = "address_detail")
  private String addressDetail;

  private String businessType; // 업태, 사용자가 직접 입력

  @OneToMany(mappedBy = "store")
  private List<StoreSchedule> storeScheduleList = new ArrayList<>();

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  private LocalDateTime deletedAt; // deletedAt != null -> soft deleted

  public static Store of(CreateStoreReq createReq, EmployerUser employerUser) {
    return new Store(
        null,
        employerUser,
        createReq.businessNumber(),
        createReq.name(),
        createReq.addressCommon(),
        createReq.addressDetail(),
        createReq.businessType(),
        new ArrayList<>(),
        null,
        null,
        null);
  }

  public void update(UpdateStoreReq updateReq) {
    this.name = updateReq.name();
    this.addressCommon = updateReq.addressCommon();
    this.addressDetail = updateReq.addressDetail();
    this.businessType = updateReq.businessType();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
    this.storeScheduleList.forEach(StoreSchedule::delete);
  }
}

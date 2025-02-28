package com.stamp.api.attendance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QRAuthCode {

    @Id
    Long storeId;

    String code;    //QR 인증 코드

    public static QRAuthCode of (Long storeId, String code) {
        return new QRAuthCode (storeId, code);
    }
}

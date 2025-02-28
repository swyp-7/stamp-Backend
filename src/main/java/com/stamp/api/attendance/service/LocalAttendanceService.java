package com.stamp.api.attendance.service;

import com.stamp.api.attendance.dto.response.QRCodeRes;
import org.springframework.security.core.userdetails.UserDetails;

public interface LocalAttendanceService {

  /** 프론트 로컬 테스트용 class */
  QRCodeRes createQR(Long storeId, UserDetails userDetails);

  QRCodeRes getQR(Long storeId, UserDetails userDetails);

  void punchIn(Long storeId, UserDetails userDetails, String authCode);

  void punchOut(Long storeId, UserDetails userDetails, String authCode);
}

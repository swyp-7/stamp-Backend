package com.stamp.api.attendance.service;

import com.stamp.api.attendance.dto.response.QRCodeRes;
import org.springframework.security.core.userdetails.UserDetails;

public interface AttendanceService {

  QRCodeRes createQR(Long storeId, UserDetails userDetails);

  QRCodeRes getQR(Long storeId, UserDetails userDetails);

  void punchIn(Long storeId, UserDetails userDetails, String authCode);

  void punchOut(Long storeId, UserDetails userDetails, String authCode);
}

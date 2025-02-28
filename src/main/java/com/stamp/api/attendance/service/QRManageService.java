package com.stamp.api.attendance.service;

public interface QRManageService {

  /** 출퇴근 인증을 위한 QR코드 생성 서비스 */
  byte[] createQRCode(Long storeId);

  byte[] getQRCode(Long storeId);

  void checkAuthCode(Long storeId, String authCode);
}

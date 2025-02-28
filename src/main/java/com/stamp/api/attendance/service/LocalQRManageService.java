package com.stamp.api.attendance.service;

public interface LocalQRManageService {

    /**
     *
     * 프론트 로컬 테스트용 class
     *
     */
    byte[] createQRCode(Long storeId);
    byte[] getQRCode(Long storeId);
    boolean checkAuthCode(Long storeId, String authCode);
}

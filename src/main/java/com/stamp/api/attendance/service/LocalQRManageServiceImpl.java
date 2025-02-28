package com.stamp.api.attendance.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.stamp.api.attendance.exception.QRCodeErrorCode;
import com.stamp.global.exception.DomainException;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class LocalQRManageServiceImpl implements LocalQRManageService {

  private HashMap<Long, String> authCodeMap; //  key: storeId, value: 인증코드
  private String defaultUrl = "localhost:3000/m/attendance/"; // 반환할 프론트 서버 url

  public LocalQRManageServiceImpl() {
    this.authCodeMap = new HashMap<>();
  }

  /**
   * Public Method Store에 인증 코드를 할당한 뒤, 이를 포함한 URL로 QR코드를 생성하여 반환
   *
   * @param storeId
   * @return QR코드 PNG파일
   */
  @Override
  public byte[] createQRCode(Long storeId) {

    String authCode = generateRandomString();
    String url = defaultUrl + authCode;
    byte[] qrCode = createQRImage(url);

    // private HashMap에 인증코드 매핑
    authCodeMap.put(storeId, authCode);

    return qrCode;
  }

  /**
   * Public Method Store의 QR 이미지 조회 함수
   *
   * @param storeId
   * @return QR코드 PNG파일
   */
  @Override
  public byte[] getQRCode(Long storeId) {

    String authCode = authCodeMap.get(storeId);
    if (authCode == null) {

      throw new DomainException(
          QRCodeErrorCode.QR_NOT_EXIST_ERROR, "QRManageServiceImpl.getQRCode");
    }

    String url = defaultUrl + authCode;
    return createQRImage(url);
  }

  /**
   * Public Method 인증코드 확인 함수
   *
   * @param storeId
   * @param authCode 인증코드
   * @return 인증 성공시 true
   */
  @Override
  public boolean checkAuthCode(Long storeId, String authCode) {

    if (!authCodeMap.containsKey(storeId)) return false;
    return authCodeMap.get(storeId).equals(authCode);
  }

  /**
   * QR코드 생성 함수
   *
   * @param url QR 생성 URL
   * @return QR코드 PNG파일
   */
  private byte[] createQRImage(String url) {
    int width = 200;
    int height = 200;

    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      // 인코딩 옵션
      Map<EncodeHintType, Object> hints = new HashMap<>();
      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

      BitMatrix bitMatrix =
          new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

      MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
      return out.toByteArray();

    } catch (Exception e) {

      throw new DomainException(QRCodeErrorCode.QR_GENERATOR_ERROR, "QRManageServiceImpl.createQR");
    }
  }

  /**
   * 인증 코드로 사용할 문자열 생성 코드
   *
   * @return 36자 이하의 문자열
   */
  private String generateRandomString() {

    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 36;
    Random random = new Random();

    return random
        .ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}

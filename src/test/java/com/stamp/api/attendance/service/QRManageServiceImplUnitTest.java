package com.stamp.api.attendance.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.stamp.api.attendance.entity.QRAuthCode;
import com.stamp.api.attendance.repository.QRAuthCodeRepository;
import com.stamp.global.exception.DomainException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QRManageServiceImplUnitTest {

  @Mock private QRAuthCodeRepository authCodeRepository;

  @InjectMocks private QRManageServiceImpl qrManageService;

  private final Long storeId = 1936263L;

  @Before
  public void setUp() {}

  @DisplayName("QR 코드 생성 - 성공 테스트")
  @Test
  public void createQRCode_Success_Test() {

    // given
    when(authCodeRepository.findByStoreId(storeId))
        .thenReturn(Optional.empty()); // storeId로 검색 시 아직 데이터가 없다고 가정

    // when
    byte[] qrCode = qrManageService.createQRCode(storeId);

    // then
    assertNotNull("QR 코드가 null이면 안 된다.", qrCode);
    assertTrue("QR 코드 바이트 배열 길이가 0보다 커야 한다.", qrCode.length > 0);

    // createQRCode() 내부에서 최종 save가 호출되었는지 검증
    verify(authCodeRepository, times(1)).save(any(QRAuthCode.class));
  }

  @DisplayName("이미 존재하는 QRAuthCode가 있을 때 다시 createQRCode() 호출 시 - 새 인증코드로 갱신 후 저장되는지")
  @Test
  public void createQRCode_UpdateExistingAuthCode_Test() {

    // given
    QRAuthCode existingAuthCode = QRAuthCode.of(storeId, "OLD_CODE");
    when(authCodeRepository.findByStoreId(storeId)).thenReturn(Optional.of(existingAuthCode));
    // 이미 storeId로 된 엔티티가 있다고 가정

    // when
    byte[] qrCode = qrManageService.createQRCode(storeId);

    // then
    assertNotNull(qrCode);
    verify(authCodeRepository, times(1)).save(any(QRAuthCode.class));
    // 기존 엔티티의 code가 새로운 랜덤 문자열로 대체되어 저장되었는지 확인하는 정도로 suffice
  }

  @DisplayName("getQRCode() - 성공 테스트")
  @Test
  public void getQRCode_Success_Test() {

    // given
    QRAuthCode existingAuthCode = QRAuthCode.of(storeId, "SOME_CODE");
    when(authCodeRepository.findByStoreId(storeId)).thenReturn(Optional.of(existingAuthCode));

    // when
    byte[] qrCode = qrManageService.getQRCode(storeId);

    // then
    assertNotNull("QR 코드 바이트 배열이 null이 아니다.", qrCode);
    assertTrue("QR 코드 길이는 0보다 커야 한다.", qrCode.length > 0);
  }

  @DisplayName("getQRCode() - 없는 storeId일 경우 DomainException 발생 테스트")
  @Test(expected = DomainException.class)
  public void getQRCode_Fail_WhenNotExist() {

    // given
    when(authCodeRepository.findByStoreId(storeId))
        .thenReturn(Optional.empty()); // 해당 StoreId가 없다고 가정

    // when
    qrManageService.getQRCode(storeId);

    // then
  }

  @DisplayName("create/get 동일성 테스트 - 같은 store에서 createQR로 만든 QR코드와 getQR로 가져오는 QR파일이 같은지")
  @Test
  public void createQR_And_getQR_identical_Test() {

    // given
    Long storeId = 1936263L;
    QRAuthCode existingAuthCode = QRAuthCode.of(storeId, "AUTH_CODE");
    when(authCodeRepository.findByStoreId(storeId)).thenReturn(Optional.of(existingAuthCode));

    // when
    byte[] qrCode1 = qrManageService.createQRCode(storeId);

    /*authCode repository가 새로 저장된 AuthCode을 반환하도록 설정 */
    ArgumentCaptor<QRAuthCode> captor = ArgumentCaptor.forClass(QRAuthCode.class);
    verify(authCodeRepository, atLeastOnce()).save(captor.capture());
    QRAuthCode savedEntity = captor.getValue();
    when(authCodeRepository.findByStoreId(storeId)).thenReturn(Optional.of(savedEntity));

    byte[] qrCode2 = qrManageService.getQRCode(storeId);

    // then
    assertArrayEquals(qrCode1, qrCode2);
  }

  @DisplayName("인증 코드 검증 - 성공 테스트")
  @Test
  public void checkAuthCode_Success_Test() {

    // given
    String rightCode = "RIGHT_CODE";
    QRAuthCode authCodeEntity = QRAuthCode.of(storeId, rightCode);

    when(authCodeRepository.findByStoreId(storeId)).thenReturn(Optional.of(authCodeEntity));

    // when
    qrManageService.checkAuthCode(storeId, rightCode);

    // then
    // 예외가 없었다면 성공으로 간주
  }

  @DisplayName("인증 코드 검증 - 잘못된 코드 입력 시 실패 테스트")
  @Test(expected = DomainException.class)
  public void checkAuthCode_FailTest() {

    // given
    String rightCode = "RIGHT_CODE";
    QRAuthCode authCodeEntity = QRAuthCode.of(storeId, rightCode);

    when(authCodeRepository.findByStoreId(storeId)).thenReturn(Optional.of(authCodeEntity));

    // when
    qrManageService.checkAuthCode(storeId, "WRONG_CODE");

    // then
    // DomainException이 발생
  }

  @DisplayName("인증 코드 검증 - storeId가 아예 없는 경우 실패 테스트")
  @Test(expected = DomainException.class)
  public void checkAuthCode_FailWhenNoStore() {

    // given
    when(authCodeRepository.findByStoreId(storeId)).thenReturn(Optional.empty());

    // when
    qrManageService.checkAuthCode(storeId, "ANY_CODE");

    // then
    // DomainException 발생
  }
}

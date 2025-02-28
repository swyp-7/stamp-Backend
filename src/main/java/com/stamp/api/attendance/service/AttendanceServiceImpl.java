package com.stamp.api.attendance.service;

import com.stamp.api.attendance.dto.response.QRCodeRes;
import com.stamp.api.attendance.entity.Attendance;
import com.stamp.api.attendance.entity.AttendanceEnum;
import com.stamp.api.attendance.exception.AttendanceErrorCode;
import com.stamp.api.attendance.repository.AttendanceRepository;
import com.stamp.api.employee.entity.Employee;
import com.stamp.api.employee.repository.EmployeeRepository;
import com.stamp.api.employeruser.repository.EmployerUserRepository;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.global.exception.DomainException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

  private final QRManageService qrManageService;
  private final StoreRepository storeRepository;
  private final EmployerUserRepository employerUserRepository;
  private final EmployeeRepository employeeRepository;
  private final AttendanceRepository attendanceRepository;

  /**
   * Public Method QR코드 생성 함수. storeId를 받아 QR코드 PNG파일을 Byte List로 반환 권한 : EmployerUser
   *
   * @param storeId 가게 Id
   * @return List<Byte> byteArr QR코드 파일
   */
  @Override
  public QRCodeRes createQR(Long storeId, UserDetails userDetails) {

    // 권한 체크
    checkEmployerUserAuthority(storeId, userDetails);

    return QRCodeRes.of(qrManageService.createQRCode(storeId));
  }

  /**
   * Public Method QR코드 조회 함수. 해당 가게에서 기존에 생성했던 QR코드를 반환한다. storeId를 받아 QR코드 PNG파일을 Byte List로 반환 권한
   * : EmployerUser
   *
   * @param storeId 가게 Id
   * @return List<Byte> byteArr QR코드 파일
   */
  @Override
  public QRCodeRes getQR(Long storeId, UserDetails userDetails) {

    // 권한 체크
    checkEmployerUserAuthority(storeId, userDetails);

    return QRCodeRes.of(qrManageService.getQRCode(storeId));
  }

  /**
   * Public Method 직원의 출근 로그를 남긴다. 권한 : Employee
   *
   * @param storeId
   */
  @Override
  public void punchIn(Long storeId, UserDetails userDetails, String authCode) {

    // 권한 체크
    checkEmployeeAuthority(storeId, userDetails);

    // 인증 코드 체크
    qrManageService.checkAuthCode(storeId, authCode);

    // 당일 출근 기록이 있는지 체크
    if (attendanceRepository.existsAttendanceByStoreIdAndEmployeeIdAndDateAndAttendance(
        storeId,
        Long.parseLong(userDetails.getUsername()),
        LocalDate.now(),
        AttendanceEnum.PUNCH_IN))
      throw new DomainException(
          AttendanceErrorCode.DUPLICATE_PUNCH_IN_ERROR, "AttendanceServiceImpl.punchIn");

    attendanceRepository.save(
        Attendance.of(
            storeId,
            Long.parseLong(userDetails.getUsername()),
            AttendanceEnum.PUNCH_IN,
            LocalDateTime.now()));
  }

  /**
   * Public Method 직원의 퇴근 로그를 남긴다. 권한 : Employee
   *
   * @param storeId
   */
  @Override
  public void punchOut(Long storeId, UserDetails userDetails, String authCode) {

    // 권한 체크
    checkEmployeeAuthority(storeId, userDetails);

    // 인증 코드 체크
    qrManageService.checkAuthCode(storeId, authCode);

    // 당일 퇴근 기록이 있는지 체크
    if (attendanceRepository.existsAttendanceByStoreIdAndEmployeeIdAndDateAndAttendance(
        storeId,
        Long.parseLong(userDetails.getUsername()),
        LocalDate.now(),
        AttendanceEnum.PUNCH_OUT))
      throw new DomainException(
          AttendanceErrorCode.DUPLICATE_PUNCH_IN_ERROR, "AttendanceServiceImpl.punchIn");

    attendanceRepository.save(
        Attendance.of(
            storeId,
            Long.parseLong(userDetails.getUsername()),
            AttendanceEnum.PUNCH_OUT,
            LocalDateTime.now()));
  }

  /** 로그인 한 유저가 Store의 EmployerUser가 맞는지 체크 권한이 없을 시 throw Exception */
  private void checkEmployerUserAuthority(Long storeId, UserDetails userDetails) {

    /*
       User가 Employer가 맞는지 체크   -> UserDetail을 구현해야함
    */

    Store store =
        storeRepository
            .findById(storeId)
            .orElseThrow(
                () ->
                    new DomainException(
                        AttendanceErrorCode.NO_STORE_ERROR,
                        "AttendanceServiceImpl.checkEmployerUserAuthority"));

    if (!store.getEmployerUser().getId().equals(Long.valueOf(userDetails.getUsername())))
      throw new DomainException(
          AttendanceErrorCode.NO_AUTHORITY_FOR_STORE_ERROR,
          "AttendanceServiceImpl.checkEmployerUserAuthority");
  }

  /** 로그인 한 유저가 Employee가 맞는지, Employee.store가 인자로 넘어온 storeId와 일치하는지 체크 권한이 없을 시 throw Exception */
  private void checkEmployeeAuthority(Long storeId, UserDetails userDetails) {

    /*
       User가 Employee가 맞는지 체크   -> UserDetail을 구현해야함
    */

    Employee employee =
        employeeRepository
            .findById(Long.parseLong(userDetails.getUsername()))
            .orElseThrow(
                () ->
                    new DomainException(
                        AttendanceErrorCode.NO_AUTHORITY_FOR_STORE_ERROR,
                        "AttendanceServiceImpl.checkEmployeeAuthority"));

    if (!employee.getStore().getId().equals(storeId))
      throw new DomainException(
          AttendanceErrorCode.NO_AUTHORITY_FOR_STORE_ERROR,
          "AttendanceServiceImpl.checkEmployerUserAuthority");
  }
}

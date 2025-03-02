package com.stamp.api.attendance.service;

import com.stamp.api.attendance.dto.requeset.AttendanceUpdateReq;
import com.stamp.api.attendance.dto.response.AttendanceRes;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

  ZoneId logTimeZone = ZoneId.of("Asia/Tokyo");

  /**
   * Public Method QR코드 생성 함수. StoreId를 받아 QR코드 PNG파일을 Byte List로 반환 권한 : EmployerUser
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
   * Public Method QR코드 조회 함수. 해당 가게에서 기존에 생성했던 QR코드를 반환한다. StoreId를 받아 QR코드 PNG파일을 Byte List로 반환 권한
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
        LocalDate.now(logTimeZone),
        AttendanceEnum.PUNCH_IN))
      throw new DomainException(
          AttendanceErrorCode.DUPLICATE_PUNCH_IN_ERROR, "AttendanceServiceImpl.punchIn");

    attendanceRepository.save(
        Attendance.of(
            storeId,
            Long.parseLong(userDetails.getUsername()),
            AttendanceEnum.PUNCH_IN,
            LocalDateTime.now(logTimeZone)));
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
        LocalDate.now(logTimeZone),
        AttendanceEnum.PUNCH_OUT))
      throw new DomainException(
          AttendanceErrorCode.DUPLICATE_PUNCH_IN_ERROR, "AttendanceServiceImpl.punchIn");

    attendanceRepository.save(
        Attendance.of(
            storeId,
            Long.parseLong(userDetails.getUsername()),
            AttendanceEnum.PUNCH_OUT,
            LocalDateTime.now(logTimeZone)));
  }

  /**
   * Public Method 가게내 전 직원의 한달 출/퇴근 로그를 조회하는 method firstDate를 포함하여 조회한다. 권한 : Employer
   *
   * @return
   */
  public List<AttendanceRes> getAttendancesForMonth(
      Long storeId, LocalDate firstDate, UserDetails userDetails) {

    // 권한 체크
    checkEmployerUserAuthority(storeId, userDetails);

    // firstDate로 Attendance Id 생성
    String id1 = createAttendanceId(storeId, firstDate);
    // firstDate + 1개월의 날짜로 Attendance Id 생성
    String id2 = createAttendanceId(storeId, firstDate.plusMonths(1));

    return attendanceRepository.findAttendancesByIdRange(id1, id2);
  }

  /**
   * Public Method 가게내 전 직원의 하루 출/퇴근 로그를 조회하는 method 권한 : Employer
   *
   * @return
   */
  public List<AttendanceRes> getAttendancesForDay(
      Long storeId, LocalDate firstDate, UserDetails userDetails) {

    // 권한 체크
    checkEmployerUserAuthority(storeId, userDetails);

    // firstDate로 Attendance Id 생성
    String id1 = createAttendanceId(storeId, firstDate);
    // firstDate + 1일의 날짜로 Attendance Id 생성
    String id2 = createAttendanceId(storeId, firstDate.plusDays(1));

    return attendanceRepository.findAttendancesByIdRange(id1, id2);
  }

  /**
   * Public Method 가게 내 특정 직원의 한달 출/퇴근 로그를 조회하는 method firstDate를 포함하여 조회한다. 권한 : Employer
   *
   * @return
   */
  public List<AttendanceRes> getAttendancesForMonthWithEmployeeId(
      Long storeId, LocalDate firstDate, Long employeeId, UserDetails userDetails) {

    // 권한 체크
    checkEmployerUserAuthority(storeId, userDetails);

    // firstDate로 Attendance Id 생성
    String id1 = createAttendanceId(storeId, firstDate);
    // firstDate + 1개월의 날짜로 Attendance Id 생성
    String id2 = createAttendanceId(storeId, firstDate.plusMonths(1));

    return attendanceRepository.findAttendancesByIdRangeAndEmployeeId(id1, id2, employeeId);
  }

  /**
   * Public Method 가게 내 특정 직원의 하루 출/퇴근 로그를 조회하는 method 권한 : Employer
   *
   * @return
   */
  public List<AttendanceRes> getAttendancesForDayWithEmployeeId(
      Long storeId, LocalDate firstDate, Long employeeId, UserDetails userDetails) {

    // 권한 체크
    checkEmployerUserAuthority(storeId, userDetails);

    // firstDate로 Attendance Id 생성
    String id1 = createAttendanceId(storeId, firstDate);
    // firstDate + 1일의 날짜로 Attendance Id 생성
    String id2 = createAttendanceId(storeId, firstDate.plusDays(1));

    return attendanceRepository.findAttendancesByIdRangeAndEmployeeId(id1, id2, employeeId);
  }

  public void updateAttendance(Long storeId, AttendanceUpdateReq req, UserDetails userDetails) {

    // store Id에 대한 권한 체크
    checkEmployerUserAuthority(storeId, userDetails);

    // employee에 대한 권한체크
    Employee employee = checkEmployerEmployeeAuthority(storeId, req);

    /*
    조회할 attendanceId 범위 설정
    store Id의 가게에서 req.date의 날에 해당하는 req.attendanceEnum(출/퇴근 구분) 중 employee Id의 기록을 조회
    */
    String id1 = createAttendanceId(storeId, req.date(), req.attendanceEnum().ordinal());
    String id2 = createAttendanceId(storeId, req.date(), req.attendanceEnum().ordinal() + 1);
    List<AttendanceRes> attendanceResList =
        attendanceRepository.findAttendancesByIdRangeAndEmployeeId(id1, id2, employee.getId());

    Attendance updatedAttendance;

    if (attendanceResList.isEmpty()
        || !attendanceRepository.existsById(attendanceResList.getFirst().id())) {

      // 출퇴근 기록이 없다면 새로 생성
      updatedAttendance =
          Attendance.of(
              storeId,
              employee.getId(),
              req.attendanceEnum(),
              LocalDateTime.of(req.date(), req.time()));
    } else {

      // 출퇴근 기록이 있다면 시간 변경
      updatedAttendance = attendanceRepository.findById(attendanceResList.getFirst().id()).get();
      updatedAttendance.setTime(req.time());
    }

    attendanceRepository.save(updatedAttendance);
  }

  /** 로그인 한 유저가 employee Id에 대한 권한이 있는지 체크. 권한이 없을 시 throw Exception */
  private Employee checkEmployerEmployeeAuthority(Long storeId, AttendanceUpdateReq req) {
    Employee employee =
        employeeRepository
            .findById(req.employeeId())
            .orElseThrow(
                () ->
                    new DomainException(
                        AttendanceErrorCode.EMPLOYEE_ID_ERROR,
                        "AttendanceServiceImpl.updateAttendance"));

    if (!storeId.equals(employee.getStore().getId())) {
      throw new DomainException(
          AttendanceErrorCode.NO_AUTHORITY_FOR_EMPLOYEE_ERROR,
          "AttendanceServiceImpl.updateAttendance");
    }
    return employee;
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

  /** 출/퇴근 로그 정보를 Attendance Id로 변환하는 메서드 */
  private static String createAttendanceId(Long storeId, LocalDate firstDate) {
    return String.format("%019d", storeId)
        + firstDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  private static String createAttendanceId(
      Long storeId, LocalDate firstDate, Integer attendanceEnum) {
    return String.format("%019d", storeId)
        + firstDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        + attendanceEnum;
  }
}

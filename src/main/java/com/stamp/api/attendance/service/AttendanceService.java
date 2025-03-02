package com.stamp.api.attendance.service;

import com.stamp.api.attendance.dto.requeset.AttendanceUpdateReq;
import com.stamp.api.attendance.dto.response.AttendanceRes;
import com.stamp.api.attendance.dto.response.QRCodeRes;
import java.time.LocalDate;
import java.util.List;

import com.stamp.api.store.dto.response.WageRes;
import org.springframework.security.core.userdetails.UserDetails;

public interface AttendanceService {

  /***************************************************
   * QR 출퇴근 관련 서비스 클래스
   * #직원용 출/퇴근 기록 조회 API는 Employee 도메인에 위치
   ***************************************************/
  QRCodeRes createQR(Long storeId, UserDetails userDetails);

  QRCodeRes getQR(Long storeId, UserDetails userDetails);

  void punchIn(Long storeId, UserDetails userDetails, String authCode);

  void punchOut(Long storeId, UserDetails userDetails, String authCode);

  List<AttendanceRes> getAttendancesForMonth(
      Long storeId, LocalDate firstDate, UserDetails userDetails);

  List<AttendanceRes> getAttendancesForDay(
      Long storeId, LocalDate firstDate, UserDetails userDetails);

  List<AttendanceRes> getAttendancesForMonthWithEmployeeId(
      Long storeId, LocalDate firstDate, Long EmployeeId, UserDetails userDetails);

  List<AttendanceRes> getAttendancesForDayWithEmployeeId(
      Long storeId, LocalDate firstDate, Long EmployeeId, UserDetails userDetails);

  void updateAttendance(Long storeId, AttendanceUpdateReq req, UserDetails userDetails);

  List<AttendanceRes> getEmployeesWorktimeForMonth(LocalDate firstDate, UserDetails userDetails);
}

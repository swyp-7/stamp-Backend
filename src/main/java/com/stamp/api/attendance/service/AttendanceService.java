package com.stamp.api.attendance.service;

import com.stamp.api.attendance.dto.requeset.AttendanceUpdateReq;
import com.stamp.api.attendance.dto.response.AttendanceRes;
import com.stamp.api.attendance.dto.response.QRCodeRes;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

public interface AttendanceService {

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
}

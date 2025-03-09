package com.stamp.api.attendance.dto.requeset;

import java.util.List;

public record AttendanceMultiUpdateReq(List<AttendanceUpdateReq> attendanceReqs) {}

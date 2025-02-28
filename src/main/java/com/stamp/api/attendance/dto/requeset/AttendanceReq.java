package com.stamp.api.attendance.dto.requeset;

import com.stamp.api.attendance.entity.AttendanceEnum;

import java.time.LocalDateTime;

public record AttendanceReq(
        String authCode //출퇴근 인증 코드
) {
}

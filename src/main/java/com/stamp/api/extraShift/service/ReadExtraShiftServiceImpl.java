package com.stamp.api.extraShift.service;

import com.stamp.api.extraShift.dto.response.ExtraShiftRes;
import com.stamp.api.extraShift.entity.ExtraShift;
import com.stamp.api.extraShift.exception.ExtraShiftErrorCode;
import com.stamp.api.extraShift.repository.ExtraShiftRepository;
import com.stamp.global.exception.DomainException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadExtraShiftServiceImpl implements ReadExtraShiftService {

  private final ExtraShiftRepository extraShiftRepository;

  @Override
  public List<ExtraShiftRes> getExtraShiftRequests(Long employeeId) {
    return extraShiftRepository.findAllByEmployeeId(employeeId).stream()
        .map(ExtraShiftRes::of)
        .toList();
  }

  @Override
  public ExtraShiftRes getExtraShiftRequestsByDate(Long employeeId, LocalDate date) {
    return ExtraShiftRes.of(findExtraShiftWithDate(employeeId, date));
  }

  private ExtraShift findExtraShiftWithDate(Long employeeId, LocalDate date) {
    return extraShiftRepository
        .findByEmployeeIdAndRequestDate(employeeId, date)
        .orElseThrow(
            () ->
                new DomainException(
                    ExtraShiftErrorCode.EXTRA_SHIFT_NOT_FOUND,
                    "ExtraShiftServiceImpl.getExtraShiftRequestsByDate"));
  }
}

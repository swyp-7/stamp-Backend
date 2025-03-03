package com.stamp.api.extraShift.service;

import com.stamp.api.extraShift.dto.response.ExtraShiftRes;
import com.stamp.api.extraShift.entity.ExtraShift;
import com.stamp.api.extraShift.entity.RequestStatus;
import com.stamp.api.extraShift.exception.ExtraShiftErrorCode;
import com.stamp.api.extraShift.repository.ExtraShiftRepository;
import com.stamp.global.exception.DomainException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateExtraShiftServiceImpl implements UpdateExtraShiftService {

  private final ExtraShiftRepository extraShiftRepository;

  @Transactional
  @Override
  public ExtraShiftRes acceptExtraShiftRequest(Long extraShiftId, Long employeeId) {
    return changeStatus(extraShiftId, employeeId, RequestStatus.ACCEPTED);
  }

  @Transactional
  @Override
  public ExtraShiftRes rejectExtraShiftRequest(Long extraShiftId, Long employeeId) {
    return changeStatus(extraShiftId, employeeId, RequestStatus.REJECTED);
  }

  private ExtraShiftRes changeStatus(Long extraShiftId, Long employeeId, RequestStatus status) {

    ExtraShift extraShift = findExtraShiftById(extraShiftId);

    if (checkEmployee(extraShift, employeeId)) {
      extraShift.changeStatus(status);
    } else {
      throw new DomainException(
          ExtraShiftErrorCode.NO_AUTHORITY_FOR_ACCEPT_REQUEST,
          "ExtraShiftServiceImpl.acceptExtraShiftRequest");
    }
    return ExtraShiftRes.of(extraShift);
  }

  private ExtraShift findExtraShiftById(Long extraShiftId) {
    return extraShiftRepository
        .findById(extraShiftId)
        .orElseThrow(
            () ->
                new DomainException(
                    ExtraShiftErrorCode.EXTRA_SHIFT_NOT_FOUND,
                    "ExtraShiftServiceImpl.createExtraShiftRequest"));
  }

  private boolean checkEmployee(ExtraShift extraShift, Long employeeId) {
    return extraShift.getEmployee().getId().equals(employeeId);
  }
}

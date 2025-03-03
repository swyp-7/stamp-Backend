package com.stamp.api.extraShift.service;

import com.stamp.api.extraShift.dto.response.ExtraShiftRes;
import com.stamp.api.extraShift.repository.ExtraShiftRepository;
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
}

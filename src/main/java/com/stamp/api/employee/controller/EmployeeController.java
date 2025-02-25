package com.stamp.api.employee.controller;

import com.stamp.api.employee.dto.request.CreateEmployeeReq;
import com.stamp.api.employee.dto.request.UpdateEmployeeReq;
import com.stamp.api.employee.dto.response.ReadEmployeeRes;
import com.stamp.api.employee.service.CreateEmployeeService;
import com.stamp.api.employee.service.ReadEmployeeService;
import com.stamp.api.employee.service.UpdateEmployeeService;
import com.stamp.global.response.ApplicationResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/store/{storeId}/employees")
@RestController
public class EmployeeController {

  private final CreateEmployeeService createEmployeeService;
  private final ReadEmployeeService readEmployeeService;
  private final UpdateEmployeeService updateEmployeeService;

  @PostMapping("/enroll")
  public void enroll(@PathVariable Long storeId, @RequestBody CreateEmployeeReq createEmployeeReq) {
    createEmployeeService.enrollEmployee(storeId, createEmployeeReq);
  }

  @GetMapping("/total")
  public ApplicationResponse<List<ReadEmployeeRes>> getAllEmployees(@PathVariable Long storeId) {
    return ApplicationResponse.ok(readEmployeeService.getAllEmployees(storeId));
  }

  @GetMapping("/{employeeId}")
  public ApplicationResponse<ReadEmployeeRes> getEmployee(@PathVariable Long employeeId) {
    return ApplicationResponse.ok(readEmployeeService.getEmployee(employeeId));
  }

  @GetMapping("/period")
  public ApplicationResponse<List<ReadEmployeeRes>> getEmployeeByPeriod(
      @PathVariable Long storeId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    return ApplicationResponse.ok(
        readEmployeeService.getEmployeeByPeriod(storeId, startDate, endDate));
  }

  @PutMapping("/{employeeId}")
  public ApplicationResponse<ReadEmployeeRes> updateEmployee(
      @PathVariable Long employeeId, @RequestBody UpdateEmployeeReq updateEmployeeReq) {
    return ApplicationResponse.ok(
        updateEmployeeService.updateEmployee(employeeId, updateEmployeeReq));
  }
}

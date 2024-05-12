package com.example.hrcoreapi.controller;

import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.service.EmployeeService;
import com.example.hrcoreapi.service.PayrollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.w3c.dom.ls.LSInput;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payroll")
public class PayrollController {
    private final PayrollService payrollService;
    private final EmployeeService employeeService;

    public PayrollController(PayrollService payrollService, EmployeeService employeeService) {
        this.payrollService = payrollService;
        this.employeeService = employeeService;
    }
    @GetMapping("/calculate-net/{employeeId}")
    public ResponseEntity<BigDecimal> calculateNetSalary(@PathVariable int employeeId) {
        try {
            YearMonth currentMonth = YearMonth.from(LocalDate.now());
            YearMonth previousMonth = currentMonth.minusMonths(  1);
            BigDecimal netSalary = payrollService.calculateSalaryForMonth(employeeId, previousMonth);
            return ResponseEntity.ok(netSalary);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid employee ID: " + employeeId, e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error calculating net salary for employee ID: " + employeeId, e);
        }
    }
    @GetMapping("/calculate-net")
    public ResponseEntity<List<BigDecimal>> calculateNetSalary() {
        try {
            YearMonth currentMonth = YearMonth.from(LocalDate.now());
            YearMonth previousMonth = currentMonth.minusMonths(  1);
            List<Employee> employeeList=employeeService.getAllEmployee();
            List<BigDecimal> netSalaries=new ArrayList<>();
            for(var employee:employeeList){
            BigDecimal netSalary = payrollService.calculateSalaryForMonth(employee.getIdEmployee(), previousMonth);
                netSalaries.add(netSalary);
            }
            return ResponseEntity.ok(netSalaries);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid employee ID: ");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error calculating net salary for employee ID: ");
        }
    }
}

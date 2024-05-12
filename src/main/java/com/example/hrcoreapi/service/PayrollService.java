package com.example.hrcoreapi.service;

import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.entities.EmployeeSalary;
import com.example.hrcoreapi.entities.PayrollRecord;
import com.example.hrcoreapi.repositories.EmployeeRepository;
import com.example.hrcoreapi.repositories.EmployeeSalaryRepository;
import com.example.hrcoreapi.repositories.PayrollRecordRepository;
import com.example.hrcoreapi.repositories.TimeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.time.DayOfWeek;


@Service
public class PayrollService {

    @Autowired
    private EmployeeSalaryRepository employeeSalaryRepository;
    @Autowired
    private TimeLogRepository timeLogRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PayrollRecordRepository payrollRecordRepository;

    private int getWorkingDaysInMonth(YearMonth month) {
        LocalDate startOfMonth = month.atDay(1);
        LocalDate endOfMonth = month.atEndOfMonth();
        int workingDays = 0;

        for (LocalDate date = startOfMonth; !date.isAfter(endOfMonth); date = date.plusDays(1)) {
            switch (date.getDayOfWeek()) {
                case MONDAY:
                case TUESDAY:
                case WEDNESDAY:
                case THURSDAY:
                case FRIDAY:
                    workingDays++;
                    break;
                default:
                    // Weekend days are not counted
                    break;
            }
        }
        return workingDays;
    }

//    public BigDecimal calculateSalaryForMonth(int employeeId, YearMonth month) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
//        EmployeeSalary currentSalary = employeeSalaryRepository.findByEmployeeAndEffectiveToIsNull(employee)
//                .orElseThrow(() -> new IllegalArgumentException("No active salary found for employee"));
//
//        int totalWorkingDays = getWorkingDaysInMonth(month);
//        List<LocalDate> daysWorked = timeLogRepository.findDistinctDaysWorked(employee, month.atDay(1).atStartOfDay(), month.atEndOfMonth().atTime(23, 59, 59));
//        int daysPresent = daysWorked.size();
//
//        if (daysPresent == totalWorkingDays) {
//            return currentSalary.getSalary();
//        } else {
//            BigDecimal dailyRate = currentSalary.getSalary().divide(new BigDecimal(totalWorkingDays), 2, RoundingMode.HALF_UP);
//            System.out.println(totalWorkingDays+" "+dailyRate);
//            return dailyRate.multiply(new BigDecimal(daysPresent));
//        }
//    }
public BigDecimal calculateSalaryForMonth(int employeeId, YearMonth month) {
    Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

    if (payrollRecordRepository.existsByEmployeeAndMonth(employee, month.getMonthValue())) {
        throw new IllegalArgumentException("Payroll record already exists for employee and month");
    }
    EmployeeSalary currentSalary = employeeSalaryRepository.findActiveSalaryForMonth(employee,month.getYear(),month.getMonthValue())
            .orElseThrow(() -> new IllegalArgumentException("No active salary found for employee"));
    int totalWorkingDays = getWorkingDaysInMonth(month);

    List<DayOfWeek> workingDays = Arrays.asList(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    );
    List<LocalDate> daysWorked = timeLogRepository.findDistinctDaysWorked(employee, month.atDay(1).atStartOfDay(), month.atEndOfMonth().atTime(23, 59, 59));
    int daysPresent = 0;
    for (LocalDate day : daysWorked) {
        if (workingDays.contains(day.getDayOfWeek())) {
           daysPresent++;
        }
    }
    System.out.println(daysPresent);
    BigDecimal salaryToPay;
    if (daysPresent == totalWorkingDays) {
        salaryToPay = currentSalary.getSalary();
    } else {
        BigDecimal dailyRate = currentSalary.getSalary().divide(new BigDecimal(totalWorkingDays), 2, RoundingMode.DOWN);
        System.out.println(daysPresent + " " + dailyRate);
        salaryToPay = dailyRate.multiply(new BigDecimal(daysPresent)).setScale(2,RoundingMode.DOWN);
    }

    BigDecimal cas = salaryToPay.multiply(new BigDecimal("0.25")).setScale(2,RoundingMode.DOWN); // Exemplu: CAS = 25% din salaryBrut
    BigDecimal cass = salaryToPay.multiply(new BigDecimal("0.1")).setScale(2,RoundingMode.DOWN); // Exemplu: CASS = 10% din salaryBrut

    BigDecimal salaryAfterDeductions = salaryToPay.subtract(cas).subtract(cass);

    BigDecimal incomeTax = salaryAfterDeductions.multiply(new BigDecimal("0.10")).setScale(2,RoundingMode.DOWN); // Exemplu: impozitul de 10%

    BigDecimal salaryNet = salaryAfterDeductions.subtract(incomeTax).setScale(2,RoundingMode.DOWN);
    System.out.println("Employee: " + employee.getIdEmployee());
    System.out.println("SalaryBrut: " +salaryToPay);
    System.out.println("Cas: " + cas);
    System.out.println("Cass: " + cass);
    System.out.println("IncomeTax: " + incomeTax);
    System.out.println("SalaryNet: " + salaryNet);
    System.out.println("Month: " + month);

    PayrollRecord salaryDetails = new PayrollRecord();
    salaryDetails.setEmployee(employee);
    salaryDetails.setSalaryBrut(salaryToPay);
    salaryDetails.setCas(cas);
    salaryDetails.setCass(cass);
    salaryDetails.setIncomeTax(incomeTax);
    salaryDetails.setSalaryNet(salaryNet);
    salaryDetails.setMonth(month.getMonthValue());
    salaryDetails.setCalculate_salary(LocalDate.now());
    payrollRecordRepository.save(salaryDetails);

    return salaryNet;
}


}

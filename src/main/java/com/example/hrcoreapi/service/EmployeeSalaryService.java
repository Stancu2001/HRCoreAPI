package com.example.hrcoreapi.service;

import com.example.hrcoreapi.dto.AddSalaryDTO;
import com.example.hrcoreapi.dto.PercentSalaryDTO;
import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.entities.EmployeeSalary;
import com.example.hrcoreapi.repositories.EmployeeSalaryRepository;
import com.example.hrcoreapi.repositories.PayrollRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class EmployeeSalaryService {
    @Autowired
    private EmployeeSalaryRepository employeeSalaryRepository;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private PayrollRecordRepository payrollRecordRepository;
    public boolean addSalary(int id, AddSalaryDTO addSalaryDTO){
        Employee employee=employeeService.findById(id);
        if(employee==null){
            return false;
        }
        EmployeeSalary employeeSalary= new EmployeeSalary();
        employeeSalary.setEmployee(employee);
        employeeSalary.setSalary(addSalaryDTO.getSalary());
        employeeSalary.setEffectiveFrom(LocalDate.now());
        employeeSalaryRepository.save(employeeSalary);
        return true;
    }
    public boolean modifySalary(int id, PercentSalaryDTO percent){
        Employee employee=employeeService.findById(id);
        if(employee==null){
            return false;
        }
        Optional<EmployeeSalary> employeeSalary=employeeSalaryRepository.findByEmployeeAndEffectiveToIsNull(employee);
        if(employeeSalary.isEmpty()){
            return false;
        }
        LocalDate currentDate = LocalDate.now();


        LocalDate lastDayOfCurrentMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        EmployeeSalary getEmployeeSalary=employeeSalary.get();
        getEmployeeSalary.setEffectiveTo(lastDayOfCurrentMonth);
        LocalDate firstDayOfNextMonth = currentDate.plusMonths(1).withDayOfMonth(1);
       BigDecimal salary=employeeSalary.get().getSalary();
       BigDecimal newSalary = null;
       if(percent.getPercent().compareTo(BigDecimal.ZERO)>0){
           newSalary=salary.add(salary.multiply(percent.getPercent().divide(new BigDecimal(100))));
       }
       else if(percent.getPercent().compareTo(BigDecimal.ZERO)<0){
           newSalary=salary.subtract(salary.multiply(percent.getPercent().abs().divide(new BigDecimal(100))));

       }
       EmployeeSalary employeeSalary1=new EmployeeSalary();
       employeeSalary1.setEmployee(employee);
       employeeSalary1.setSalary(newSalary);
       employeeSalary1.setEffectiveFrom(firstDayOfNextMonth);
       employeeSalaryRepository.save(employeeSalary1);
       employeeSalaryRepository.save(getEmployeeSalary);
       return true;
    }
}

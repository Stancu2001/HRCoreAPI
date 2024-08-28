package com.example.hrcoreapi.controller;

import com.example.hrcoreapi.dto.AddSalaryDTO;
import com.example.hrcoreapi.dto.PercentSalaryDTO;
import com.example.hrcoreapi.entities.EmployeeSalary;
import com.example.hrcoreapi.service.EmployeeSalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary")
public class EmployeeSalaryController {
    @Autowired
    EmployeeSalaryService employeeSalaryService;

    @PostMapping("/add/{id}")
    public ResponseEntity<?> AddSalary(@PathVariable int id, @Valid @RequestBody AddSalaryDTO addSalaryDTO){
        boolean addSalary=employeeSalaryService.addSalary(id,addSalaryDTO);
        if(!addSalary){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> modifySalary(@PathVariable int id, @Valid @RequestBody PercentSalaryDTO percentSalaryDTO){
        boolean modifySalary=employeeSalaryService.modifySalary(id,percentSalaryDTO);
        if(!modifySalary){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}

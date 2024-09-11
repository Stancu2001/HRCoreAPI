package com.example.hrcoreapi.controller;

import com.example.hrcoreapi.dto.CreateAddressDTO;
import com.example.hrcoreapi.dto.CreateEmployeeDTO;
import com.example.hrcoreapi.dto.UpdateEmployeeFunctionDTO;
import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.repositories.DepartmentalFunctionsRepository;
import com.example.hrcoreapi.repositories.EmployeeRepository;
import com.example.hrcoreapi.service.EmployeeServiceImpl;
import jakarta.persistence.Tuple;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@PreAuthorize("hasAnyRole('User','Admin')")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;

    @PostMapping("/save")
    public ResponseEntity<?> createEmployee(@Valid @RequestPart("Address") CreateAddressDTO createAddressDTO, @Valid @RequestPart("Employee") CreateEmployeeDTO createEmployeeDTO,@Valid @RequestPart("idfunctie") UpdateEmployeeFunctionDTO updateEmployeeFunctionDTO){
        boolean saveUser=employeeService.createEmployee(createAddressDTO,createEmployeeDTO,updateEmployeeFunctionDTO);
        if(!saveUser){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployee() {
        List<Employee> employeeList=employeeService.getAllEmployee();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
       Employee employee=employeeService.findById(id);
       if(employee==null){
           return ResponseEntity.badRequest().build();
       }
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id){
        boolean deleteEmployee=employeeService.deleteEmployee(id);
        if(!deleteEmployee){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }


//    @PutMapping("/{id}/functions")
//    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody UpdateEmployeeFunctionDTO updateEmployeeFunctionDTO){
//        Employee deleteEmployee=employeeService.updateEmployeeFunctionDTO(id,updateEmployeeFunctionDTO.getId());
//        if(deleteEmployee==null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
//    @PutMapping("/{id}/address")
//    public ResponseEntity<?> updateAddress(@PathVariable int id, @Valid @RequestBody CreateAddressDTO addressDTO){
//        Employee deleteEmployee=employeeService.updateEmployeeAddress(id,addressDTO);
//        if(deleteEmployee==null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateEmployee(@PathVariable int id, @Valid @RequestBody CreateEmployeeDTO employeeDTO){
//        Employee deleteEmployee=employeeService.updateEmployee(id,employeeDTO);
//        if(deleteEmployee==null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAllDetailsEmployee(@PathVariable int id, @Valid @RequestPart("Address") CreateAddressDTO createAddressDTO, @Valid @RequestPart("Employee") CreateEmployeeDTO createEmployeeDTO,@Valid @RequestPart("idfunctie") UpdateEmployeeFunctionDTO updateEmployeeFunctionDTO){
        int employee= employeeService.updateDetailsEmployee(id, createAddressDTO, createEmployeeDTO, updateEmployeeFunctionDTO);
        switch (employee){
            case 1: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            case 2: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Function not found");
            case 4: return ResponseEntity.ok("Employee updated successfully");
            default: return ResponseEntity.internalServerError().body("Internal server error");
        }
    }
}

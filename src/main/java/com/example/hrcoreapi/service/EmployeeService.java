package com.example.hrcoreapi.service;

import com.example.hrcoreapi.dto.CreateAddressDTO;
import com.example.hrcoreapi.dto.CreateEmployeeDTO;
import com.example.hrcoreapi.dto.UpdateEmployeeFunctionDTO;
import com.example.hrcoreapi.entities.Employee;

import java.util.List;

public interface EmployeeService {
    void saveEmployee(Employee employee);
    boolean createEmployee(CreateAddressDTO createAddressDTO, CreateEmployeeDTO createEmployeeDTO, UpdateEmployeeFunctionDTO updateEmployeeFunctionDTO);

    List<Employee> getAllEmployee();
    Employee findById(int id);

    boolean deleteEmployee(int id);

    Employee updateEmployeeFunctionDTO(int id, int functionID);

    Employee updateEmployeeAddress(int id, CreateAddressDTO createAddressDTO);

    Employee updateEmployee(int id, CreateEmployeeDTO createEmployeeDTO);

    int updateDetailsEmployee(int id, CreateAddressDTO createAddressDTO, CreateEmployeeDTO createEmployeeDTO, UpdateEmployeeFunctionDTO updateEmployeeFunctionDT);
}

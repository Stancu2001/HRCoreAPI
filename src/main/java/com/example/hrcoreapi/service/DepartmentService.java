package com.example.hrcoreapi.service;

import com.example.hrcoreapi.dto.CreateDepartmentDTO;
import com.example.hrcoreapi.dto.DepartmentDTO;
import com.example.hrcoreapi.entities.Department;
import com.example.hrcoreapi.entities.Employee;

import java.util.List;

public interface DepartmentService {
    boolean createDepartment(CreateDepartmentDTO createDepartmentDTO);

    void saveDepartment(Department department);

    boolean deleteDepartment(int IdDepartment);

    DepartmentDTO getDepartment(int id);

    boolean updateDepartment(int id,CreateDepartmentDTO createDepartmentDTO);
    List<DepartmentDTO> getAllDepartment();

    List<Employee> getEmployeesByDepartment(int departmentId);
    Department getDepartmentByID(int id);
}

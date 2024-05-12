package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.entities.Department;
import com.example.hrcoreapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("SELECT e FROM Employee e JOIN e.departmentalFunctions df WHERE df.department.id = :departmentId")
    List<Employee> findAllEmployeesByDepartmentId(int departmentId);
}

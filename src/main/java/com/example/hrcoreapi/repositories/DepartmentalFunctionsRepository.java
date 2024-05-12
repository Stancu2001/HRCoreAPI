package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.dto.DepartmentalFunctionsDTO;
import com.example.hrcoreapi.dto.EmployeeInfoDTO;
import com.example.hrcoreapi.entities.DepartmentalFunctions;
import com.example.hrcoreapi.entities.Employee;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentalFunctionsRepository extends JpaRepository<DepartmentalFunctions,Integer> {
    List<DepartmentalFunctions> findByDepartmentIdDepartment(int departmentId);
    @Query("SELECT e FROM Employee e WHERE e.departmentalFunctions.functionId = :functionId")
    List<Employee> findEmployeesByFunctionId(int functionId);
}

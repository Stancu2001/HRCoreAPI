package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.dto.EmployeeInfoDTO;
import com.example.hrcoreapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query("SELECT e FROM Employee e WHERE e.departmentalFunctions.department.idDepartment = :idDepartment")
    List<Employee> findAllEmployeesByDepartmentId(@Param("idDepartment") int idDepartment);

}

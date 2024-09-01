package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.dto.EmployeeInfoDTO;
import com.example.hrcoreapi.entities.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

//    @EntityGraph(attributePaths = {"departmentalFunctions"})
//    List<Employee> findByDepartmentalFunctions_Department_IdDepartment(int departmentId);

    @Query(value = "SELECT * FROM employee e " +
            "JOIN departmental_functions df ON e.department_functions_id = df.id_functie " +
            "JOIN department d ON df.department_id = d.id_departament " +
            "WHERE d.id_departament = :departmentId", nativeQuery = true)
    List<Employee> findEmployeesByDepartmentId(@Param("departmentId") int departmentId);

}

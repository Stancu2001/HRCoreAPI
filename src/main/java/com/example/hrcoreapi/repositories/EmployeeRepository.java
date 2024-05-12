package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.dto.EmployeeInfoDTO;
import com.example.hrcoreapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

}

package com.example.hrcoreapi.dto;

import com.example.hrcoreapi.entities.DepartmentalFunctions;
import com.example.hrcoreapi.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private int id;
    private String name;
    List<DepartmentalFunctions> functions;
    List<Employee> employeeList;
}

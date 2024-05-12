package com.example.hrcoreapi.service;

import com.example.hrcoreapi.dto.Create_Department_FunctionDTO;
import com.example.hrcoreapi.dto.DepartmentalFunctionsDTO;
import com.example.hrcoreapi.entities.DepartmentalFunctions;

import java.util.List;

public interface DepartmentalFunctionsService {
    void save(DepartmentalFunctions departmentalFunctions);
    boolean create_function(int idDepartment,Create_Department_FunctionDTO createDepartmentFunctionDTO);
    List<DepartmentalFunctionsDTO> getFunctionsByDepartment(int departmentId);
    boolean deleteFunction(int functionId);
    boolean updateFunction(int id, Create_Department_FunctionDTO createDepartmentFunctionDTO);
}

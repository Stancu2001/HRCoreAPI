package com.example.hrcoreapi.service;

import com.example.hrcoreapi.dto.CreateDepartmentDTO;
import com.example.hrcoreapi.dto.Create_Department_FunctionDTO;
import com.example.hrcoreapi.dto.DepartmentalFunctionsDTO;
import com.example.hrcoreapi.entities.Department;
import com.example.hrcoreapi.entities.DepartmentalFunctions;
import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.repositories.DepartmentalFunctionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentalFunctionsServiceImpl implements DepartmentalFunctionsService{
    @Autowired
    DepartmentalFunctionsRepository departmentalFunctionsRepository;

    @Autowired
    DepartmentService departmentService;

    @Override
    public void save(DepartmentalFunctions departmentalFunctions){
        departmentalFunctionsRepository.save(departmentalFunctions);
    }
    @Override
    public int create_function(int idDepartment,Create_Department_FunctionDTO createDepartmentFunctionDTO){
        try {

            var getDepartment= departmentService.getDepartmentByID(idDepartment);
            if(getDepartment==null){
                return 1;
            }

            DepartmentalFunctions departmentalFunctions=new DepartmentalFunctions();
            departmentalFunctions.setFunctionName(createDepartmentFunctionDTO.getName());
            departmentalFunctions.setDepartment(getDepartment);
            save(departmentalFunctions);
            return 1<<1;
        }catch (Exception e){
            return 1<<2;
        }
    }

    @Override
    public List<DepartmentalFunctionsDTO> getFunctionsByDepartment(int departmentId) {
        List<DepartmentalFunctions> functions = departmentalFunctionsRepository.findByDepartmentIdDepartment(departmentId);
        return functions.stream()
                .map(function -> new DepartmentalFunctionsDTO(function.getFunctionId(), function.getFunctionName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteFunction(int functionId){
        DepartmentalFunctions departmentalFunctions=departmentalFunctionsRepository.findById(functionId).orElse(null);
        if(departmentalFunctions==null){
            return false;
        }
        List<Employee> employeeList= departmentalFunctionsRepository.findEmployeesByFunctionId(functionId);
        if(!employeeList.isEmpty()){
            return false;
        }
        var department=departmentalFunctions.getDepartment();
        department.getFunctions().remove(departmentalFunctions);
        departmentalFunctionsRepository.delete(departmentalFunctions);
        return  true;
    }

    @Override
    public boolean updateFunction(int id, Create_Department_FunctionDTO createDepartmentFunctionDTO){
        DepartmentalFunctions departmentalFunctions =departmentalFunctionsRepository.findById(id).orElse(null);
        if(departmentalFunctions==null){
            return false;
        }
        departmentalFunctions.setFunctionName(createDepartmentFunctionDTO.getName());
        save(departmentalFunctions);
        return true;
    }
}

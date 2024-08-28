package com.example.hrcoreapi.service;


import com.example.hrcoreapi.dto.CreateDepartmentDTO;
import com.example.hrcoreapi.dto.DepartmentDTO;
import com.example.hrcoreapi.entities.Department;
import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.repositories.DepartmentRepository;
import com.example.hrcoreapi.repositories.EmployeeRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void saveDepartment(Department department){
        departmentRepository.save(department);
    }
    @Override
    public boolean createDepartment(CreateDepartmentDTO createDepartmentDTO){
        try {

        Department createDepartment= new Department();
        createDepartment.setNameDepartment(createDepartmentDTO.getName());
        saveDepartment(createDepartment);
        return true;
        }catch (Exception e){
            return false;
        }
    }
    @Override
    public List<DepartmentDTO> getAllDepartment(){
        List<Department> departmentList=departmentRepository.findAll();
        List<DepartmentDTO> viewDepartment=new ArrayList<>();
        for(var department:departmentList){
            DepartmentDTO departmentDTO=new DepartmentDTO();
            departmentDTO.setId(department.getIdDepartment());
            departmentDTO.setName(department.getNameDepartment());
            viewDepartment.add(departmentDTO);
        }
        return viewDepartment;
    }
    @Override
    public DepartmentDTO getDepartment(int id) {
        var department=departmentRepository.findById(id).orElse(null);
        var employee= employeeRepository.findAllEmployeesByDepartmentId(id);
        var departamentDTO= new DepartmentDTO();
        assert department != null;
        departamentDTO.setId(department.getIdDepartment());
        departamentDTO.setName(department.getNameDepartment());
        departamentDTO.setFunctions(department.getFunctions());
        departamentDTO.setEmployeeList(employee);
        return  departamentDTO;
    }

    @Override
    public  boolean updateDepartment(int id,CreateDepartmentDTO createDepartmentDTO){
        Department department=departmentRepository.findById(id).orElse(null);
        if(department==null){
            return false;
        }
        department.setNameDepartment(createDepartmentDTO.getName());
        saveDepartment(department);
        return true;
    }

    @Override
    public boolean deleteDepartment(int IdDepartment){
        List<Employee> employeeList=departmentRepository.findAllEmployeesByDepartmentId(IdDepartment);
        if(departmentRepository.existsById(IdDepartment)&& employeeList.isEmpty()) {
            departmentRepository.deleteById(IdDepartment);
            return true;
        }
        return false;
    }
    @Override
    public List<Employee> getEmployeesByDepartment(int departmentId) {
        return departmentRepository.findAllEmployeesByDepartmentId(departmentId);
    }
}

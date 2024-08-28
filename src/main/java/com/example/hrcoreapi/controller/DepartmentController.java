package com.example.hrcoreapi.controller;

import com.example.hrcoreapi.dto.CreateDepartmentDTO;
import com.example.hrcoreapi.dto.DepartmentDTO;
import com.example.hrcoreapi.entities.Department;
import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @PostMapping("/save")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody CreateDepartmentDTO createDepartmentDTO){
        boolean saveDepartment= departmentService.createDepartment(createDepartmentDTO);
        if(!saveDepartment){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
    @GetMapping("/allDepartment")
    public ResponseEntity<?> getAllDepartment(){
        List<DepartmentDTO> departmentList=departmentService.getAllDepartment();
        return ResponseEntity.ok(departmentList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable int id){
        var department=departmentService.getDepartment(id);

        if(department==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable int id,@Valid @RequestBody CreateDepartmentDTO createDepartmentDTO){
        boolean updateDepartment=departmentService.updateDepartment(id,createDepartmentDTO);
        if(!updateDepartment){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") int IdDepartment){

        boolean deleteDepartment= departmentService.deleteDepartment(IdDepartment);
        if(!deleteDepartment){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{departmentId}/employees")
    public List<Employee> getEmployeesByDepartment(@PathVariable int departmentId) {
        return departmentService.getEmployeesByDepartment(departmentId);
    }

}

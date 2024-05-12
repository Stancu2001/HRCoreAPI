package com.example.hrcoreapi.controller;

import com.example.hrcoreapi.dto.CreateDepartmentDTO;
import com.example.hrcoreapi.dto.Create_Department_FunctionDTO;
import com.example.hrcoreapi.dto.DepartmentalFunctionsDTO;
import com.example.hrcoreapi.service.DepartmentalFunctionsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/function")
public class DepartmentalFunctionsController {
    @Autowired
    DepartmentalFunctionsService departmentalFunctionsService;

    @PostMapping("{id}/save")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody Create_Department_FunctionDTO createDepartmentFunctionDTO, @PathVariable int id){
        boolean saveFunction= departmentalFunctionsService.create_function(id,createDepartmentFunctionDTO);
        if(!saveFunction){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/getAll")
    public ResponseEntity<?> getFunctionDepartment(@PathVariable int id){
        List<DepartmentalFunctionsDTO> getFunction= departmentalFunctionsService.getFunctionsByDepartment(id);
        return ResponseEntity.ok(getFunction);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFunction(@PathVariable int id){
        boolean delete= departmentalFunctionsService.deleteFunction(id);
        if(!delete){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFunction(@PathVariable int id,@Valid @RequestBody Create_Department_FunctionDTO createDepartmentFunctionDTO){
        boolean update= departmentalFunctionsService.updateFunction(id,createDepartmentFunctionDTO);
        if(!update){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}

package com.example.hrcoreapi.service;

import com.example.hrcoreapi.dto.CreateAddressDTO;
import com.example.hrcoreapi.dto.CreateEmployeeDTO;
import com.example.hrcoreapi.dto.UpdateEmployeeFunctionDTO;
import com.example.hrcoreapi.entities.*;
import com.example.hrcoreapi.repositories.DepartmentalFunctionsRepository;
import com.example.hrcoreapi.repositories.EmployeeRepository;
import com.example.hrcoreapi.repositories.QrCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    DepartmentalFunctionsRepository departmentalFunctionsRepository;
    @Autowired
    QrCodeRepository qrCodeRepository;

    @Override
     public void saveEmployee(Employee employee){
         employeeRepository.save(employee);
     }
     @Override
      public boolean createEmployee(CreateAddressDTO createAddressDTO, CreateEmployeeDTO createEmployeeDTO, UpdateEmployeeFunctionDTO updateEmployeeFunctionDTO){
         try {
             System.out.println("Test");
             Address address=new Address();
             address.setCountry(createAddressDTO.getCountry());
             address.setCity(createAddressDTO.getCity());
             address.setStreet(createAddressDTO.getStreet());
             address.setNumber(createAddressDTO.getNumber());
             address.setApartment(createAddressDTO.getApartment());
             address.setStaircase(createAddressDTO.getStaircase());
             address.setFloor(createAddressDTO.getFloor());
             Employee employee=new Employee();
             employee.setEmail(createEmployeeDTO.getEmail());
             employee.setFirstName(createEmployeeDTO.getFirstName());
             employee.setLastName(createEmployeeDTO.getLastName());
             employee.setAge(createEmployeeDTO.getAge());
             employee.setPhone(createEmployeeDTO.getPhone());
             employee.setNationality(createEmployeeDTO.getNationality());
             employee.setGender(createEmployeeDTO.getGender());
             employee.setRegistered(LocalDate.now());
             employee.setBankCode(createEmployeeDTO.getBankCode());
             System.out.println("TESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS "+createEmployeeDTO.getBankCode());
             employee.setCnp(createEmployeeDTO.getCnp());
             employee.setRemarks(createEmployeeDTO.getRemarks());
             DepartmentalFunctions departmentalFunctions=departmentalFunctionsRepository.findById(updateEmployeeFunctionDTO.getId()).orElse(null);
             if(departmentalFunctions==null){
                 return false;
             }
             System.out.println("test1");
             address= addressService.saveAddress(address);
             employee.setAddress(address);
             employee.setDepartmentalFunctions(departmentalFunctions);
             QrCode qrCode=new QrCode();
             qrCode.setCode(UUID.randomUUID());
             System.out.println(qrCode.getCode());
             QrCode qrCode1=qrCodeRepository.save(qrCode);
             employee.setQrCode(qrCode1);
             saveEmployee(employee);
         return true;
         }catch (Exception e){
             e.printStackTrace();
             return false;
         }
      }
      @Override
      public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
      }

      @Override
    public Employee findById(int id){
          Employee employee = employeeRepository.findById(id).orElse(null);
          if (employee != null) {
              List<TimeLog> timeLogs = employee.getTimeLogs();
              Collections.sort(timeLogs, Comparator.comparing(TimeLog::getTimestamp).reversed());
          }
          return employee;
      }

      @Override
    public boolean deleteEmployee(int id){
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
      }
      @Override
      public Employee updateEmployeeFunctionDTO(int id, int functionID){
        Employee employee=employeeRepository.findById(id).orElse(null);
        if(employee==null){
            return null;
        }
        DepartmentalFunctions departmentalFunctions=departmentalFunctionsRepository.findById(functionID).orElse(null);
        if(departmentalFunctions==null){
            return null;
        }
        employee.setDepartmentalFunctions(departmentalFunctions);
        saveEmployee(employee);
        return employee;
      }
    @Override
    public Employee updateEmployeeAddress(int id, CreateAddressDTO createAddressDTO){
        Employee employee=employeeRepository.findById(id).orElse(null);
        if(employee==null){
            return null;
        }
        Address employee_address=employee.getAddress();
        employee_address.setCountry(createAddressDTO.getCountry());
        employee_address.setCity(createAddressDTO.getCity());
        employee_address.setStreet(createAddressDTO.getStreet());
        employee_address.setNumber(createAddressDTO.getNumber());
        employee_address.setApartment(createAddressDTO.getApartment());
        employee_address.setStaircase(createAddressDTO.getStaircase());
        employee_address.setFloor(createAddressDTO.getFloor());
        employee_address= addressService.saveAddress(employee_address);
        employee.setAddress(employee_address);
        return employee;
    }
    @Override
    public Employee updateEmployee(int id, CreateEmployeeDTO createEmployeeDTO){
        Employee employee=employeeRepository.findById(id).orElse(null);
        if(employee==null){
            return null;
        }
        employee.setEmail(createEmployeeDTO.getEmail());
        employee.setFirstName(createEmployeeDTO.getFirstName());
        employee.setLastName(createEmployeeDTO.getLastName());
        employee.setAge(createEmployeeDTO.getAge());
        employee.setPhone(createEmployeeDTO.getPhone());
        employee.setNationality(createEmployeeDTO.getNationality());
        employee.setGender(createEmployeeDTO.getGender());
        employee.setRegistered(LocalDate.now());
        employee.setCnp(createEmployeeDTO.getCnp());
        employee.setRemarks(createEmployeeDTO.getRemarks());
        saveEmployee(employee);
        return employee;
    }

}

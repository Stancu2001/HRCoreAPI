package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.entities.PayrollRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRecordRepository extends JpaRepository<PayrollRecord, Long> {
    boolean existsByEmployeeAndMonth(Employee employee, int month);
}

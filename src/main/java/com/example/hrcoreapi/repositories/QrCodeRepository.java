package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.entities.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode,Integer> {
    @Query("SELECT q.employee FROM QrCode q WHERE q.code = :code")
    Employee findEmployeeByCode(UUID code);
}

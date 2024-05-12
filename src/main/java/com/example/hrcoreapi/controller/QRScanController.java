package com.example.hrcoreapi.controller;

import com.example.hrcoreapi.dto.QrCodeDTO;
import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.entities.TimeLog;
import com.example.hrcoreapi.repositories.EmployeeRepository;
import com.example.hrcoreapi.repositories.QrCodeRepository;
import com.example.hrcoreapi.repositories.TimeLogRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500/")
@RestController
@RequestMapping("/api/qr")
public class QRScanController {

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Autowired
    private TimeLogRepository timeLogRepository;

    @PostMapping("/scan")
    public ResponseEntity<Object> registerScan(@Valid @RequestBody QrCodeDTO qrCodeId) {
        System.out.println(qrCodeId.getCode());
        Employee employee = qrCodeRepository.findEmployeeByCode(qrCodeId.getCode());
        if (employee == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid QR Code scanned"));
        }

        TimeLog lastLog = timeLogRepository.findTopByEmployeeOrderByIdDesc(employee);
        String type = (lastLog == null || "exit".equals(lastLog.getType())) ? "entry" : "exit";

        TimeLog log = new TimeLog();
        log.setEmployee(employee);
        log.setTimestamp(LocalDateTime.now());
        log.setType(type);
        timeLogRepository.save(log);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Time logged successfully for " + employee.getCnp() + ". Type: " + type);
        return ResponseEntity.ok(response);
    }

}

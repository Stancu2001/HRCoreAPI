package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.entities.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeLogRepository extends JpaRepository<TimeLog,Integer> {
    TimeLog findTopByEmployeeOrderByIdDesc(Employee employee);
    @Query("SELECT DISTINCT CAST(log.timestamp AS LocalDate) FROM TimeLog log " +
            "WHERE log.employee = :employee AND log.timestamp BETWEEN :start AND :end " +
            "AND EXISTS (SELECT 1 FROM TimeLog sub WHERE sub.employee = log.employee AND CAST(sub.timestamp AS LocalDate) = CAST(log.timestamp AS LocalDate) AND sub.type = 'entry') " +
            "AND EXISTS (SELECT 1 FROM TimeLog sub WHERE sub.employee = log.employee AND CAST(sub.timestamp AS LocalDate) = CAST(log.timestamp AS LocalDate) AND sub.type = 'exit')")
    List<LocalDate> findDistinctDaysWorked(@Param("employee") Employee employee, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}

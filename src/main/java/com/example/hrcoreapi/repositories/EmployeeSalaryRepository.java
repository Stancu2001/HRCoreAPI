package com.example.hrcoreapi.repositories;

import com.example.hrcoreapi.entities.Employee;
import com.example.hrcoreapi.entities.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.YearMonth;
import java.util.Optional;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary,Integer> {
    Optional<EmployeeSalary> findByEmployeeAndEffectiveToIsNull(Employee employee);
@Query("SELECT es FROM EmployeeSalary es " +
        "WHERE es.employee = :employee " +
        "AND (" +
        "(YEAR(es.effectiveFrom) < :year OR " +
        "(YEAR(es.effectiveFrom) = :year AND MONTH(es.effectiveFrom) <= :month)) " +
        "AND (" +
        "(YEAR(es.effectiveTo) > :year) OR " +
        "(YEAR(es.effectiveTo) = :year AND MONTH(es.effectiveTo) >= :month) OR " +
        "es.effectiveTo IS NULL)) " +
        "ORDER BY es.effectiveTo DESC")
Optional<EmployeeSalary> findActiveSalaryForMonth(@Param("employee") Employee employee, @Param("year") int year, @Param("month") int month);
}

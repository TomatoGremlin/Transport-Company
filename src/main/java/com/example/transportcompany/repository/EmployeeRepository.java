package com.example.transportcompany.repository;

import com.example.transportcompany.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Query to retrieve employees sorted by driver qualification
    @Query("SELECT e FROM Employee e ORDER BY e.name ASC")
    List<Employee> sortedByQualification();
    // Query to filter employees by driver qualification
    @Query("SELECT DISTINCT e FROM Employee e JOIN e.qualifications vt WHERE vt.id = :vehicleTypeId")
    List<Employee> filteredByQualification(@Param("vehicleTypeId") long vehicleTypeId);

    // Query to retrieve employees sorted by salary
    @Query("SELECT e FROM Employee e ORDER BY e.salary ASC")
    List<Employee> sortedBySalary();


    // Query to filter employees by salary
    @Query("SELECT e FROM Employee e WHERE e.salary < :salary")
    List<Employee> filteredBySalaryGreaterThan(BigDecimal salary);
}

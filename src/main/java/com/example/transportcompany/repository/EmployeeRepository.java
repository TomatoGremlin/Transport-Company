package com.example.transportcompany.repository;

import com.example.transportcompany.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Query to retrieve employees sorted by name
    @Query("SELECT e FROM Employee e ORDER BY e.name ASC")
    List<Employee> findAllSortedByName();
    // Query to filter employees by name
    @Query("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE %:name% ORDER BY e.name ASC")
    List<Employee> findByDriverQualification(String name);

    // Query to retrieve employees sorted by salary
    @Query("SELECT e FROM Employee e ORDER BY e.salary ASC")
    List<Employee> findAllSortedBySalary();


    // Query to filter employees by salary
    @Query("SELECT e FROM Employee e WHERE e.salary = :salary")
    List<Employee> filterBySalary(BigDecimal salary);
}

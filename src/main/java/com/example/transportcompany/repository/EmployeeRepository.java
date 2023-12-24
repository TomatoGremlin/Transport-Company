package com.example.transportcompany.repository;

import com.example.transportcompany.model.Employee;
import com.example.transportcompany.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByCompanyId(Long companyId);
  /*  @Query("SELECT e FROM Employee e JOIN e.qualifications q WHERE q.type = :qualificationName ORDER BY e.ty")
    List<Employee> sortedByQualification();*/
    @Query("SELECT DISTINCT e FROM Employee e JOIN e.qualifications vt WHERE vt.id = :vehicleTypeId")
    List<Employee> filteredByQualification(@Param("vehicleTypeId") long vehicleTypeId);

    @Query("SELECT e FROM Employee e ORDER BY e.salary")
    List<Employee> sortedBySalary();

    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> filteredBySalaryGreaterThan(BigDecimal salary);
}

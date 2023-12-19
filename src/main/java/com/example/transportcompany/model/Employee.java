package com.example.transportcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "employee")
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    long id;


    @ManyToOne()
    @JoinColumn(name = "company_id")
    @JsonIgnore
    TransportCompany company;

    @NotNull(message = "Employee name cannot be null")
    @NotBlank(message = "Employee name cannot be left blank")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Employee names should start with a capital letter followed by lowercase")
    @Column(name="employee_name", nullable = false)
    String name;

    @Digits(integer = 5, fraction = 2, message = "Maximum 5 digits with 2 decimal places allowed")
    @Column(name="salary")
    BigDecimal salary;

    @ManyToMany
    @JoinTable(
            name = "employee_has_vehicle",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    Set<Vehicle> vehicles;


    @ManyToMany
    @JoinTable(
            name = "employee_has_qualification",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_type_id")
    )
    Set<VehicleType> qualifications;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    Set<Transportation> transportations;


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", company=" + company.getId() +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", vehicles=" + vehicles +
                ", qualifications=" + qualifications +
                '}';
    }
}

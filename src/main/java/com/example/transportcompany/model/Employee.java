package com.example.transportcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "employee")
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    long id;

    @NotNull(message = "Employee name cannot be null")
    @NotBlank(message = "Employee name cannot be left blank")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Employee names should start with a capital letter followed by lowercase")
    @Size(max = 20, message = "Employee name has to be with up to 20 characters")
    @Column(name="employee_name", nullable = false)
    String name;

    @NotNull(message = "Salary cannot be null")
    @Positive
    @Digits(integer = 5, fraction = 2, message = "Maximum 5 digits with 2 decimal places allowed")
    @DecimalMin(value = "933", message = "Value must be equal to or greater than 933")
    @Column(name="salary", nullable = false)
    BigDecimal salary;

    @NotNull(message = "Employee has to be hired in a Company")
    @ManyToOne()
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    TransportCompany company;

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

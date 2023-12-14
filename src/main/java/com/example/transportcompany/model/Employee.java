package com.example.transportcompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
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
    TransportCompany company;

    //not null
    @NotBlank(message = "Employee name cannot be left blank")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Employee names should start with a capital letter followed by lowercase")
    @Column(name="employee_name", nullable = false)
    String name;

    @ManyToMany
    @JoinTable(
            name = "employee_has_vehicle",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    Set<Vehicle> vehicleList;


    @ManyToMany
    @JoinTable(
            name = "employee_drives_vehicle_type",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_type_id")
    )
    Set<VehicleType>vehicleTypeList;

    @OneToMany(mappedBy = "employee")
    Set<Transportation>transportationList;

}

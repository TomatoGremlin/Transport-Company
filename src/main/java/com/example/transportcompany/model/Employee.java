package com.example.transportcompany.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
    long employeeID;

    @ManyToOne()
    @JoinTable(name = "transport_company_id")
    TransportCompany company;

    @Column(name="employee_name")
    String name;

    @ManyToMany
    @JoinTable(
            name = "employee_has_vehicle",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    List<Vehicle>vehicleList;


    @ManyToMany
    @JoinTable(
            name = "employee_drives_vehicle_type",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_type_id")
    )
    List<VehicleType>vehicleTypeList;

    @OneToMany(mappedBy = "employeeList")
    List<Transportation>transportationList;

}

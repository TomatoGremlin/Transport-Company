package com.example.transportcompany.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vehicle")
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    long id;

    @ManyToOne()
    @JoinColumn(name = "company_id") // This is the foreign key column
    TransportCompany company;

    @ManyToOne()
    @JoinColumn(name= "vehicle_type_id")
    VehicleType vehicleType;

    @ManyToMany(mappedBy = "vehicleList")
    Set<Employee> employeeList;

}

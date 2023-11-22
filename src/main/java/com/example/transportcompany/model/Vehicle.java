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
@Table(name = "vehicle")
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    long vehicleID;

    @ManyToOne()
    @JoinColumn(name = "transport_company_id") // This is the foreign key column
    TransportCompany company;

    @OneToOne()
    @JoinColumn(name=  "vehicle_id")
    VehicleType vehicleType;

    @ManyToMany(mappedBy = "vehicleList")
    List<Employee>employeeList;

}

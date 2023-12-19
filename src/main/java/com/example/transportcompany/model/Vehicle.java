package com.example.transportcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @JoinColumn(name = "company_id", nullable = false) // This is the foreign key column
    TransportCompany company;

    @NotNull
    @ManyToOne()
    @JoinColumn(name= "vehicle_type_id", nullable = false)
    VehicleType vehicleType;

    @JsonIgnore
    @ManyToMany(mappedBy = "vehicleList")
    Set<Employee> employeeList;

}

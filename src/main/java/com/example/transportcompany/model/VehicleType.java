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
@Table(name = "vehicle_type")
@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vehicle_type_id")
    long vehicleTypeID;

    @OneToOne(mappedBy = "vehicleType")
    Vehicle vehicle;

    @ManyToMany(mappedBy = "vehicleTypeList")
    List<Employee> employeeList;
}

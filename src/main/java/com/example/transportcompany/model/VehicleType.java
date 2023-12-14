package com.example.transportcompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "vehicle_type")
@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vehicle_type_id")
    long id;

    @NotBlank(message = "Vehicle type cannot be left blank")
    @Pattern(regexp = "^([a-z]).*", message = "Vehicle types begin with lowercase letters")
    @Column(name="type", nullable = false)
    String type;

    @OneToMany(mappedBy = "vehicleType")
    Set<Vehicle> vehicles;

    @ManyToMany(mappedBy = "vehicleTypeList")
    Set<Employee> employeeList;
}
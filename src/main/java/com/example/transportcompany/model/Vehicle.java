package com.example.transportcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
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

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "company_id", nullable = false) // This is the foreign key column
    TransportCompany company;

    @NotNull
    @ManyToOne()
    @JoinColumn(name= "vehicle_type_id", nullable = false)
    VehicleType vehicleType;

    @JsonIgnore
    @ManyToMany(mappedBy = "vehicles")
    Set<Employee> employeeList;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleType=" + vehicleType.getType() +
                '}';
    }
}

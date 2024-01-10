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
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vehicle")
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    long id;

    @NotNull(message = "Vehicle Type cannot be null")
    @ManyToOne()
    @JoinColumn(name= "vehicle_type_id", nullable = false)
    VehicleType vehicleType;

    @NotNull(message = "Company cannot be null")
    @ManyToOne()
    @JoinColumn(name = "company_id", nullable = false) // This is the foreign key column
    @JsonIgnore
    TransportCompany company;

    @ManyToMany(mappedBy = "vehicles")
    @JsonIgnore
    Set<Employee> employees;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleType=" + vehicleType.getType() +
                '}';
    }
}

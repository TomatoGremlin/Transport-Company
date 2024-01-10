package com.example.transportcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vehicle_type")
@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vehicle_type_id")
    long id;

    @NotNull(message = "Vehicle type cannot be null")
    @NotBlank(message = "Vehicle type cannot be left blank")
    @Pattern(regexp = "^([a-z]).*", message = "Vehicle types has to be all lowercase letters")
    @Size(max = 15, message = "Company name has to be with up to 15 characters")
    @Column(name="type", nullable = false)
    String type;

    @OneToMany(mappedBy = "vehicleType")
    @JsonIgnore
    Set<Vehicle> vehicles;

    @ManyToMany(mappedBy = "qualifications")
    @JsonIgnore
    Set<Employee> employees;

    @Override
    public String toString() {
        return "VehicleType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}

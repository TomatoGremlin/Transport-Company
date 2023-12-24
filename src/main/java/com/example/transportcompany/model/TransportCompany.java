package com.example.transportcompany.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transport_company")
@Entity
public class TransportCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    long id;

    @NotNull(message = "Company name cannot be null")
    @NotBlank(message = "Company name cannot be left blank")
    @Size(max = 30, message = "Company name has to be with up to 30 characters")
    @Pattern(regexp = "^([A-Z]).*", message = "Company names begin with capital letters")
    @Column(name = "company_name", nullable = false)
    String companyName;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Employee> employees;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    Set<Vehicle> vehicles;


    @OneToMany(mappedBy = "company")
    @JsonIgnore
    Set<Transportation> transportations;

    @OneToOne(mappedBy = "company")
    @JsonIgnore
    TransportationRate transportationRate;

    @Override
    public String toString() {
        return "TransportCompany{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", employeeList=" + employees +
                ", vehicleList=" + vehicles +
                ", transportationList=" + transportations +
                ", transportationRate=" + transportationRate.getId() +
                '}';
    }
}

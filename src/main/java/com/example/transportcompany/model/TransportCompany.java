package com.example.transportcompany.model;
import jakarta.validation.constraints.*;

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
    Set<Employee> employeeList;

    @OneToMany(mappedBy = "company")
    Set<Vehicle>vehicleList;

    @OneToMany(mappedBy = "company")
    Set<Transportation>transportationList;

    @OneToOne(mappedBy = "company")
    TransportationRate transportationRate;

}

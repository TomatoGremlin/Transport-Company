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
@Table(name = "transport_company")
@Entity
public class TransportCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_company_id")
    long transportCompanyID;

    @Column(name = "company_name")
    String companyName;

    @OneToMany(mappedBy = "company")
    List<Employee>employeeList;

    @OneToMany(mappedBy = "company")
    List<Vehicle>vehicleList;


    @OneToMany(mappedBy = "company")
    List<Transportation>transportationList;

}

package com.example.transportcompany.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transportation")
@Entity
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transportation_id")
    UUID id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    TransportCompany company;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @Column(name="start_point")
    String startPoint;

    @Column(name="end_point")
    String endPoint;

    @Column(name="departure_date")
    LocalDate departureDate;

    @Column(name="arrival_date")
    LocalDate arrivalDate;

    @Column(name="payment_status")
    boolean paymentStatus;



    @ManyToMany
    @JoinTable(
            name = "transportation_has_customer",
            joinColumns = @JoinColumn(name = "transportation_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    List<Customer>customerList;


    @ManyToMany
    @JoinTable(
            name = "transportation_has_load",
            joinColumns = @JoinColumn(name = "transportation_id"),
            inverseJoinColumns = @JoinColumn(name = "load_id")

    )
    List<Load>loadList;


}

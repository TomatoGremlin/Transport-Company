package com.example.transportcompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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
    long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    TransportCompany company;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @NotBlank(message = "Start point cannot be left blank")
    @Column(name="start_point")
    String startPoint;

    @NotBlank(message = "End point cannot be left blank")
    @Column(name="end_point")
    String endPoint;

    @NotNull(message = "Departure date cannot be null")
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
    Set<Customer> customerList;


    @ManyToMany
    @JoinTable(
            name = "transportation_has_load",
            joinColumns = @JoinColumn(name = "transportation_id"),
            inverseJoinColumns = @JoinColumn(name = "load_id")

    )
    Set<Load>loadList;


}

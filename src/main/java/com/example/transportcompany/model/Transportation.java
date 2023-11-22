package com.example.transportcompany.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    long transportationID;

    @ManyToOne
    @JoinColumn(name = "transport_company_id")
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



}

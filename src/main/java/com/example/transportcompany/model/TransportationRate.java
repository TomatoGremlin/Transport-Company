package com.example.transportcompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transportation_rate")
@Entity
public class TransportationRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    long id;


    // price per person
    @NotNull(message = "Customer rate cannot be null")
    @Positive
    @Digits(integer = 10, fraction = 2, message = "Maximum 10 digits with 2 decimal places allowed")
    @DecimalMin(value = "1.0", inclusive = true, message = "Customer rate must be greater than or equal to 1.0 lv")
    @Column(name ="customer_rate", nullable = false)
    BigDecimal customerRate;

    // price per kilogram
    @NotNull(message = "Load rate cannot be null")
    @Positive
    @Digits(integer = 10, fraction = 2, message = "Maximum 10 digits with 2 decimal places allowed")
    @DecimalMin(value = "1.0", inclusive = true, message = "Load rate must be greater than or equal to 1.0 lv")
    @Column(name ="load_rate", nullable = false)
    BigDecimal loadRate;

    @OneToOne
    @JoinColumn(name = "company_id")
    TransportCompany company;
}

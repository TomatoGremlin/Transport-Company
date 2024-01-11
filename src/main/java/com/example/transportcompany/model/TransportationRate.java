package com.example.transportcompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transportation_rate")
@Entity
public class TransportationRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    long id;

    // price per person
    @NotNull(message = "Customer Rate cannot be null")
    @Digits(integer = 2, fraction = 2, message = "Maximum 2 digits with 2 decimal places allowed")
    @DecimalMin(value = "1.0", inclusive = true, message = "Customer Rate must be greater than or equal to 1.0 lv")
    @Column(name ="customer_rate", nullable = false)
    BigDecimal customerRate;

    // price per kilogram
    @NotNull(message = "Load Rate cannot be null")
    @Digits(integer = 2, fraction = 2, message = "Maximum 2 digits with 2 decimal places allowed")
    @DecimalMin(value = "1.0", inclusive = true, message = "Load Rate must be greater than or equal to 1.0 lv")
    @Column(name ="load_rate", nullable = false)
    BigDecimal loadRate;

    @NotNull(message = "Company cannot be null")
    @OneToOne
    @JoinColumn(name = "company_id", nullable = false)
    TransportCompany company;

    @Override
    public String toString() {
        return "TransportationRate{" +
                "id=" + id +
                ", customerRate=" + customerRate +
                ", loadRate=" + loadRate +
                ", company=" + company.getId() +
                '}';
    }
}

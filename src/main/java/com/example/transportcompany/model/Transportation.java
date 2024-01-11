package com.example.transportcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transportation")
@Entity
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transportation_id")
    long id;

    @NotNull(message = "Transportation start point cannot be null")
    @NotBlank(message = "Start point cannot be left blank")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Employee names should start with a capital letter followed by lowercase")
    @Size(max = 20, message = "Company name has to be with up to 20 characters")
    @Column(name="start_point", nullable = false)
    String startPoint;

    @NotNull(message = "Transportation end point cannot be null")
    @NotBlank(message = "End point cannot be left blank")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Employee names should start with a capital letter followed by lowercase")
    @Size(max = 20, message = "Company name has to be with up to 20 characters")
    @Column(name="end_point", nullable = false)
    String endPoint;

    @NotNull(message = "Departure date cannot be null")
    @Column(name="departure_date", nullable = false)
    LocalDate departureDate;

    @NotNull(message = "Arrival date cannot be null")
    @Column(name="arrival_date", nullable = false)
    LocalDate arrivalDate;

    @NotNull(message = "Payment status cannot be null")
    @Column(name="payment_status", nullable = false)
    boolean paymentStatus;

    @NotNull(message = "Company cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    TransportCompany company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    Employee employee;

    @ManyToMany
    @JoinTable(
            name = "transportation_has_customer",
            joinColumns = @JoinColumn(name = "transportation_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    Set<Customer> customers;


    @ManyToMany
    @JoinTable(
            name = "transportation_has_load",
            joinColumns = @JoinColumn(name = "transportation_id"),
            inverseJoinColumns = @JoinColumn(name = "load_id")
    )
    Set<Load> loads;

    @Override
    public String toString() {
        return "Transportation{" +
                "id=" + id +
                ", company=" + company.getId() +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}

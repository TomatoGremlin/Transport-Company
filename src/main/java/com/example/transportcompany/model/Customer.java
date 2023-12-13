package com.example.transportcompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    long id;

    @NotBlank(message = "Customer name cannot be left blank")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Customer names should start with a capital letter followed by lowercase")
    @Column(name="customer_name", nullable = false)
    String customerName;

    @ManyToMany(mappedBy = "customerList")
    Set<Transportation> transportationList;

}

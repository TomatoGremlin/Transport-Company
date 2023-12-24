package com.example.transportcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
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

    @NotNull(message = "Customer name cannot be null")
    @NotBlank(message = "Customer name cannot be left blank")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Customer names should start with a capital letter followed by lowercase")
    @Column(name="customer_name", nullable = false)
    String customerName;

    @JsonIgnore
    @ManyToMany(mappedBy = "customers")
    Set<Transportation> transportations;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}

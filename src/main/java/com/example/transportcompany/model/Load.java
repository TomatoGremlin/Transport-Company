package com.example.transportcompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "`load`")
@Entity
public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="load_id")
    long id;

    @Positive
    //@Min(value = "1.0", inclusive = true, message = "Weight must be at least 1.0 kg")
    //@Digits(integer = 5, fraction = 2, message = "Maximum 5 digits with 2 decimal places allowed")
    @Column(name = "weight")
    double weight;

    @ManyToMany(mappedBy = "loadList")
    Set<Transportation> transportationList;

}

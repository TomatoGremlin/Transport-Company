package com.example.transportcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
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

    @NotNull(message = "Load weight name cannot be null")
    @Positive
    //@Min(value = "1.0", inclusive = true, message = "Weight must be at least 1.0 kg")
    //@Max
    //@Digits(integer = 5, fraction = 2, message = "Maximum 5 digits with 2 decimal places allowed")
    @Column(name = "weight", nullable = false)
    double weight;

    @JsonIgnore
    @ManyToMany(mappedBy = "loadList")
    Set<Transportation> transportationList;

    @Override
    public String toString() {
        return "Load{" +
                "id=" + id +
                ", weight=" + weight +
                '}';
    }
}

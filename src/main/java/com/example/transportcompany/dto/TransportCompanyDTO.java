package com.example.transportcompany.dto;

import com.example.transportcompany.model.Employee;
import com.example.transportcompany.model.Transportation;
import com.example.transportcompany.model.TransportationRate;
import com.example.transportcompany.model.Vehicle;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportCompanyDTO {
    String companyName;
}

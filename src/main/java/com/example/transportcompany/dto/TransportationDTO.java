package com.example.transportcompany.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportationDTO {
    long companyId;
    String startPoint;
    String endPoint;
    LocalDate departureDate;
    LocalDate arrivalDate;
    boolean paymentStatus;
}

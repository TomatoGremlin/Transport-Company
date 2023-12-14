package com.example.transportcompany.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportationRateDTO {
    long companyId;
    BigDecimal customerRate;
    BigDecimal loadRate;

}

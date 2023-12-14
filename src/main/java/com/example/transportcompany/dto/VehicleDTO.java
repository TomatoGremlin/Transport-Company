package com.example.transportcompany.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleDTO {
    long vehicleTypeId;
    long companyId;
}

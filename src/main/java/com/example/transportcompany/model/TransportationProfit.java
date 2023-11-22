package com.example.transportcompany.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transportation_profit")
@Entity
public class TransportationProfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transportation_profit_id")
    long profitID;

    @Column(name="transportation_price")
    BigDecimal transportation_price;

}

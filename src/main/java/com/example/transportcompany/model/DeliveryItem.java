package com.example.transportcompany.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "delivery_item")
@Entity
public class DeliveryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_item_id")
    long deliveryItemID;

    @Column(name="item_weight")
    double itemWeight;

}

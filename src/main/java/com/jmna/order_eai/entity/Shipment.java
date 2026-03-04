package com.jmna.order_eai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Shipment {
    @EmbeddedId
    private ShipmentId id;

    @Column
    private String orderId;

    @Column
    private String itemId;

    @Column
    private String address;
}

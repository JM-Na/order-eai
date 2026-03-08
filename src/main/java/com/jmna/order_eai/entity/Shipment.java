package com.jmna.order_eai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "SHIPMENT_TB"
//        , schema = "RECRUIT"
)
@NoArgsConstructor
public class Shipment {
    @EmbeddedId
    private ShipmentId id;

    @Column
    private String orderId;

    @Column
    private String itemId;

    @Column
    private String address;

    @Builder
    public Shipment(String shipmentId, String applicantId, String orderId, String itemId, String address) {
        this.id = new ShipmentId(shipmentId, applicantId);
        this.orderId = orderId;
        this.itemId = itemId;
        this.address = address;
    }
}

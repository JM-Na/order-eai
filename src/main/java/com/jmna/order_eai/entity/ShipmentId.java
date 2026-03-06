package com.jmna.order_eai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@Embeddable
public class ShipmentId implements Serializable {
    @Column
    private String shipmentId;

    @Column
    private String applicantId;

    public ShipmentId(String shipmentId, String applicantId) {
        this.shipmentId = shipmentId;
        this.applicantId = applicantId;
    }
}

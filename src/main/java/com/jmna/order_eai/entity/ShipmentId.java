package com.jmna.order_eai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ShipmentId implements Serializable {
    @Column
    private String shipmentId;

    @Column
    private String applicantId;
}

package com.jmna.order_eai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 복합키 shipmentId, applicantId를 위한 클래스
 */
@Getter
@NoArgsConstructor
@Embeddable
public class ShipmentId implements Serializable {
    @Column
    private String shipmentId;

    @Column
    private String applicantKey;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public ShipmentId(String shipmentId, String applicantKey) {
        this.shipmentId = shipmentId;
        this.applicantKey = applicantKey;
    }
}

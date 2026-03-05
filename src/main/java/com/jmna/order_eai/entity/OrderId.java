package com.jmna.order_eai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@Embeddable
public class OrderId implements Serializable {
    // 형식: 알파벳 1개 + 숫자 3개 (ex: A113)
    @Column
    private String orderId;

    @Column
    private String applicantId;

    public OrderId(String orderId, String applicantId) {
        this.orderId = orderId;
        this.applicantId = applicantId;
    }
}

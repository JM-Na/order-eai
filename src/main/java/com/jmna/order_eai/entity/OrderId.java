package com.jmna.order_eai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderId implements Serializable {
    // 형식: 알파벳 1개 + 숫자 3개 (ex: A113)
    @Column
    private Long orderId;

    @Column
    private String applicantId;
}

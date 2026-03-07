package com.jmna.order_eai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 복합키 orderId, applicantId를 위한 클래스
 */
@Getter
@NoArgsConstructor
@Embeddable
public class OrderId implements Serializable {
    // 형식: 알파벳 1개 + 숫자 3개 (ex: A113)
    @Column
    private String orderId;

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

    public OrderId(String orderId, String applicantKey) {
        this.orderId = orderId;
        this.applicantKey = applicantKey;
    }
}

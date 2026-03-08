package com.jmna.order_eai.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Table(name = "ORDER_TB"
        , schema = "RECRUIT"
)
@Entity
@NoArgsConstructor
public class Order {

    @EmbeddedId
    private OrderId id;

    @Column
    private String userId;

    @Column
    private String itemId;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String itemName;

    @Column
    private String price;

    @Column
    private String status;

    @Builder
    public Order(String orderId, String applicantKey, String userId, String itemId, String name, String address, String itemName, String price, String status) {
        this.id = new OrderId(orderId, applicantKey);
        this.userId = userId;
        this.itemId = itemId;
        this.name = name;
        this.address = address;
        this.itemName = itemName;
        this.price = price;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

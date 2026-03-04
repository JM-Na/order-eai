package com.jmna.order_eai.entity;

import jakarta.persistence.*;

@Table(name = "ORDER_TB")
@Entity
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
}

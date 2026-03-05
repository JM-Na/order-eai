package com.jmna.order_eai.repository;

import com.jmna.order_eai.entity.Order;
import com.jmna.order_eai.entity.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderId> {
}

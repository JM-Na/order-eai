package com.jmna.order_eai.repository;

import com.jmna.order_eai.entity.Order;
import com.jmna.order_eai.entity.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderId> {
    // OrderId 리스트에 해당하는 Order를 대상으로 Status 업데이트
    @Modifying
    @Query("""
            UPDATE Order o
            SET o.status = 'Y'
            WHERE o.id IN :orderIdList
            """)
    void updateStatus(List<OrderId> orderIdList);
}

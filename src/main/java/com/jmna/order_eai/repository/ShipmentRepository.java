package com.jmna.order_eai.repository;

import com.jmna.order_eai.entity.Shipment;
import com.jmna.order_eai.entity.ShipmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, ShipmentId> {
}

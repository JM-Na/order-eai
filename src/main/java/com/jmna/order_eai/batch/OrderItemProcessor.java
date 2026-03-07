package com.jmna.order_eai.batch;

import com.jmna.order_eai.entity.Order;
import com.jmna.order_eai.entity.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class OrderItemProcessor implements ItemProcessor<Order, Shipment> {

    @Value("${inspien.applicant.key}")
    private String applicantKey;

    @Override
    public Shipment process(Order order) throws Exception {
        String shipmentId = order.getId().getOrderId().replace("A", "S00");
        return Shipment.builder()
                .shipmentId(shipmentId)
                .applicantId(applicantKey)
                .orderId(order.getId().getOrderId())
                .itemId(order.getItemId())
                .address(order.getAddress())
                .build();
    }
}

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
        return Shipment.builder()
                .shipmentId(String.valueOf(UUID.randomUUID()))
                .applicantId(applicantKey)
                .orderId(order.getId().getOrderId())
                .itemId(order.getItemId())
                .address(order.getAddress())
                .build();
    }
}

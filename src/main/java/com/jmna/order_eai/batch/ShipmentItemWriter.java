package com.jmna.order_eai.batch;

import com.jmna.order_eai.entity.Order;
import com.jmna.order_eai.entity.OrderId;
import com.jmna.order_eai.entity.Shipment;
import com.jmna.order_eai.repository.OrderRepository;
import com.jmna.order_eai.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShipmentItemWriter implements ItemWriter<Shipment> {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;


    @Override
    public void write(Chunk<? extends Shipment> chunk) throws Exception {
        log.info("{}개의 청크 처리 완료", chunk.size());
        shipmentRepository.saveAll(chunk);

        List<OrderId> orderIdList = chunk.getItems()
                .stream()
                .map(s ->
                        new OrderId(s.getOrderId(),s.getId().getApplicantId()))
                .toList();

        orderRepository.updateStatus(orderIdList);
    }
}

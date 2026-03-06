package com.jmna.order_eai.service;

import com.jmna.order_eai.dto.HeaderXml;
import com.jmna.order_eai.dto.ItemXml;
import com.jmna.order_eai.dto.RequestXml;
import com.jmna.order_eai.entity.Order;
import com.jmna.order_eai.entity.OrderIdGenerator;
import com.jmna.order_eai.repository.OrderRepository;
import com.jmna.order_eai.repository.OrderSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderSequenceRepository orderSequenceRepository;
    private final ReceiptFileService receiptFileService;

    @Value("${inspien.applicant.key}")
    private String applicantKey;

    public Map<HeaderXml, List<ItemXml>> validate(RequestXml requestXml) {
        // Header 맵, key: USER_ID, value: HeaderXml
        Map<String, HeaderXml> headerMap = requestXml.getHeaders().stream()
                .collect(Collectors.toMap(
                        HeaderXml::getUserId,
                        h -> h
                ));

        // Header Item 맵, key: HeaderXml, value: List<ItemXml>
        Map<HeaderXml, List<ItemXml>> headerItemMap = new HashMap<>();

        // Item 리스트를 순회하며 Item의 userId가 Header 맵 내에서 존재하는지 확인
        for (ItemXml item : requestXml.getItems()) {
            String userId = item.getUserId();

            // Header가 존재하지 않는 Item의 경우
            if (!headerMap.containsKey(userId)) {
                System.out.println("존재하지 않는 userId = " + userId + "를 참조하는 아이템 itemId = "+ item.getItemId());
            }

            else {
                // Header Item 맵에 해당하는 값 추가
                headerItemMap
                        .computeIfAbsent(headerMap.get(userId), k -> new ArrayList<>())
                        .add(item);
            }

        }

        // Header 리스트를 순회하며 Header에 해당하는 Item이 존재하는지 확인
        for (HeaderXml header : requestXml.getHeaders()) {
            // Item이 존재하지 않는 Header의 경우
            if (!headerItemMap.containsKey(header)) {
                System.out.println("header.getUserId() = " + header.getUserId());
            }
        }

        return headerItemMap;
    }

    public void saveOrder(Map<HeaderXml, List<ItemXml>> headerItemMap) throws IOException {
        List<Order> orderList = new ArrayList<>();
        for (HeaderXml headerXml : headerItemMap.keySet()) {

            List<ItemXml> itemXmlList = headerItemMap.get(headerXml);

            for (ItemXml itemXml : itemXmlList) {
                long nextSequence = orderSequenceRepository.getNextSequence();

                String orderId = OrderIdGenerator.generate(nextSequence);

                Order order = Order.builder()
                        .orderId(orderId)
                        .applicantKey(applicantKey)
                        .userId(headerXml.getUserId())
                        .itemId(itemXml.getItemId())
                        .name(headerXml.getName())
                        .address(headerXml.getAddress())
                        .itemName(itemXml.getItemName())
                        .price(itemXml.getPrice())
                        .status(headerXml.getName())
                        .build();

                orderList.add(order);
            }
        }
        orderRepository.saveAll(orderList);

        receiptFileService.createReceipt(orderList);
    }
}

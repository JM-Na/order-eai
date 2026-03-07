package com.jmna.order_eai.service;

import com.jmna.order_eai.dto.*;
import com.jmna.order_eai.entity.Order;
import com.jmna.order_eai.entity.OrderIdGenerator;
import com.jmna.order_eai.repository.OrderRepository;
import com.jmna.order_eai.repository.OrderSequenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderSequenceRepository orderSequenceRepository;
    private final ReceiptFileService receiptFileService;
    private final FtpService ftpService;

    @Value("${inspien.applicant.key}")
    private String applicantKey;

    public SyncResult processOrder(RequestXml request) {
        ValidateResult validateResult = validate(request);

        Integer successCount = validateResult.getSuccessCount();
        Integer failCount = validateResult.getFailCount();
        List<ErrorDetail> errors = validateResult.getErrors();

        Map<HeaderXml, List<ItemXml>> headerItemMap = validateResult.getHeaderItemMap();

        List<Order> orders = saveOrder(headerItemMap);
        Boolean ftp = sendOrderFtp(orders);

        if (!ftp) {
            return new SyncResult("Fail", new ErrorDetail(null, null, "FTP 전송 오류"));
        }

        return new SyncResult(successCount, failCount, errors);
    }

    private ValidateResult validate(RequestXml requestXml) {
        // Header 맵, key: USER_ID, value: HeaderXml
        Map<String, HeaderXml> headerMap = requestXml.getHeaders().stream()
                .collect(Collectors.toMap(
                        HeaderXml::getUserId,
                        h -> h
                ));

        int count = headerMap.size() + requestXml.getItems().size();
        int failCount = 0;
        List<ErrorDetail> errors = new ArrayList<>();


        // Header Item 맵, key: HeaderXml, value: List<ItemXml>
        Map<HeaderXml, List<ItemXml>> headerItemMap = new HashMap<>();

        // Item 리스트를 순회하며 Item의 userId가 Header 맵 내에서 존재하는지 확인
        for (ItemXml item : requestXml.getItems()) {
            String userId = item.getUserId();

            // Header가 존재하지 않는 Item의 경우
            if (!headerMap.containsKey(userId)) {
                log.error("존재하지 않는 userId = {}를 참조하는 아이템 itemId = {}", userId, item.getItemId());
                failCount++;
                errors.add(new ErrorDetail(null, item.getItemId(), "존재하지 않는 userId = " + userId + "를 참조하는 아이템 itemId = " + item.getItemId()));
            } else {
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
                log.error("Item이 존재하지 않는 Header의 userId: {}", header.getUserId());
                failCount++;
                errors.add(new ErrorDetail(header.getUserId(), null, "Item이 존재하지 않는 Header의 userId: " + header.getUserId()));
            }
        }

        return new ValidateResult(headerItemMap, count - failCount, failCount, errors);
    }

    @Transactional
    private List<Order> saveOrder(Map<HeaderXml, List<ItemXml>> headerItemMap) {
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
                        .status(headerXml.getStatus().trim())
                        .build();

                orderList.add(order);
            }
        }
        orderRepository.saveAll(orderList);
        log.info("주문 정보 저장 성공");

        return orderList;
    }


    private Boolean sendOrderFtp(List<Order> orderList) {
        try {
            File receipt = receiptFileService.createReceipt(orderList);
            log.info("주문 정보 기반 영수증 생성 성공");

            ftpService.transferFile(receipt);
            log.info("영수증 FTP 전송 성공");
        } catch (IOException e) {
            log.error("주문 처리 중 오류", e);
            return false;
        }

        return true;
    }
}

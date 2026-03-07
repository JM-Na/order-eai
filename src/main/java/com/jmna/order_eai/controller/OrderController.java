package com.jmna.order_eai.controller;

import com.jmna.order_eai.dto.*;
import com.jmna.order_eai.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/receive", consumes = MediaType.APPLICATION_XML_VALUE)
    public SyncResult receive(@RequestBody RequestXml request) throws IOException {
        
        log.info("주문 정보 요청 발생");

        ValidateResult validateResult = orderService.validate(request);

        Integer successCount = validateResult.getSuccessCount();
        Integer failCount = validateResult.getFailCount();
        List<ErrorDetail> errors = validateResult.getErrors();

        Map<HeaderXml, List<ItemXml>> headerItemMap = validateResult.getHeaderItemMap();

        orderService.saveOrder(headerItemMap);

        return new SyncResult(successCount, failCount, errors);
    }

}

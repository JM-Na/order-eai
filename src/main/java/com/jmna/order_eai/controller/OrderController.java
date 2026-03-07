package com.jmna.order_eai.controller;

import com.jmna.order_eai.dto.*;
import com.jmna.order_eai.service.FtpService;
import com.jmna.order_eai.service.OrderService;
import com.jmna.order_eai.service.ReceiptFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ReceiptFileService receiptFileService;
    private final FtpService ftpService;

    @ResponseBody
    @PostMapping(value = "/receive", consumes = MediaType.APPLICATION_XML_VALUE)
    public SyncResult receive(@RequestBody RequestXml request) throws IOException {
        
        log.info("주문 정보 요청 발생");

        return orderService.processOrder(request);
    }

}

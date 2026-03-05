package com.jmna.order_eai.controller;

import com.jmna.order_eai.dto.HeaderXml;
import com.jmna.order_eai.dto.ItemXml;
import com.jmna.order_eai.dto.RequestXml;
import com.jmna.order_eai.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/receive", consumes = MediaType.APPLICATION_XML_VALUE)
    public void receive(@RequestBody RequestXml request) throws IOException {
        System.out.println("request = " + request);

        Map<HeaderXml, List<ItemXml>> headerItemMap = orderService.validate(request);

        orderService.saveOrder(headerItemMap);
    }

}

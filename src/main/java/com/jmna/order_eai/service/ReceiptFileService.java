package com.jmna.order_eai.service;

import com.jmna.order_eai.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class ReceiptFileService {
    public File createReceipt(List<Order> orderList) throws IOException {
        Path path = Paths.get(createFileName());
        try {
            for (Order order : orderList) {
                String orderInfo = order.getId().getOrderId() + "^" +
                        order.getUserId() + "^" +
                        order.getItemId() + "^" +
                        order.getId().getApplicantId() + "^" +
                        order.getName() + "^" +
                        order.getAddress() + "^" +
                        order.getItemName() + "^" +
                        order.getPrice() + "\\n\n";

                Files.write(
                        path,
                        orderInfo.getBytes(),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            }
        } catch (IOException e) {
            log.error("영수증 파일 입력 중 오류가 발생했습니다.");
            throw new IOException("영수증 파일 입력 중 오류가 발생했습니다.", e);
        }
        return path.toFile();
    }

    private String createFileName() {
        LocalDateTime now = LocalDateTime.now();

        String localDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        return "INSPIEN_나종명_" + localDateTime + ".txt";
    }
}

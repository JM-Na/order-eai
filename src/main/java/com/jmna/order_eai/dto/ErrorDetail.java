package com.jmna.order_eai.dto;

import lombok.Data;

@Data
public class ErrorDetail {
    private String userId;
    private String itemId;
    private String detail;

    public ErrorDetail(String userId, String itemId, String detail) {
        this.userId = userId;
        this.itemId = itemId;
        this.detail = detail;
    }
}

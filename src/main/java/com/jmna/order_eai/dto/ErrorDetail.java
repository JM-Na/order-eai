package com.jmna.order_eai.dto;

import lombok.Data;

/**
 * 에러 정보를 저장할 클래스
 */
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

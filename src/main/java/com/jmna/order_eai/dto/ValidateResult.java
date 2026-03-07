package com.jmna.order_eai.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 주문 정보 검증 결과를 담을 DTO
 */
@Data
public class ValidateResult {
    private Map<HeaderXml, List<ItemXml>> headerItemMap;
    private Integer successCount;
    private Integer failCount;
    private List<ErrorDetail> errors;

    public ValidateResult(Map<HeaderXml, List<ItemXml>> headerItemMap, Integer successCount, Integer failCount, List<ErrorDetail> errors) {
        this.headerItemMap = headerItemMap;
        this.successCount = successCount;
        this.failCount = failCount;
        this.errors = errors;
    }
}

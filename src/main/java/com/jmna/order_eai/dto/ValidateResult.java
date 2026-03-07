package com.jmna.order_eai.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

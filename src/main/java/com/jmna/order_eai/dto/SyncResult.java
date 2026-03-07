package com.jmna.order_eai.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 주문 정보 요청 Sync에 대한 응답 클래스
 */
@Data
public class SyncResult {

    private String result;
    private int successCount;
    private int failCount;
    private List<ErrorDetail> errors = new ArrayList<>();

    public SyncResult(String result, ErrorDetail error) {
        this.result = result;
        this.errors.add(error);
    }

    public SyncResult(int successCount, int failCount, List<ErrorDetail> errors) {
        if (failCount == 0)
            this.result = "Success";
        else if (successCount == 0)
            this.result = "Fail";
        else
            this.result = "Partial Success";

        this.successCount = successCount;
        this.failCount = failCount;
        this.errors = errors;
    }
}

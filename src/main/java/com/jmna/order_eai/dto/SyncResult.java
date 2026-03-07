package com.jmna.order_eai.dto;

import lombok.Data;

import java.util.List;

@Data
public class SyncResult {

    private String result;
    private int successCount;
    private int failCount;
    private List<ErrorDetail> errors;

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

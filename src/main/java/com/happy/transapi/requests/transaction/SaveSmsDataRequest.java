
package com.happy.transapi.requests.transaction;

import com.google.gson.annotations.Expose;

public class SaveSmsDataRequest {

    @Expose
    private String transactionData;
    @Expose
    private Long userId;

    public String getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(String transactionData) {
        this.transactionData = transactionData;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}

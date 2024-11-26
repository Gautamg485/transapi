
package com.happy.transapi.reponses;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

public class SmsDataResponse {

    @Expose
    private String smsData;
    @Expose
    private List bankNameRegex;
    @Expose
    private List transactionTypeRegex;
    @Expose
    private List merchantRegex;
    @Expose
    private List scamRegex;

    @Expose
    private Date smsLastUpdatedAt;

    public String getSmsData() {
        return smsData;
    }

    public void setSmsData(String smsData) {
        this.smsData = smsData;
    }

    public Date getSmsLastUpdatedAt() {
        return smsLastUpdatedAt;
    }

    public void setSmsLastUpdatedAt(Date smsLastUpdatedAt) {
        this.smsLastUpdatedAt = smsLastUpdatedAt;
    }

    public List getBankNameRegex() {
        return bankNameRegex;
    }

    public void setBankNameRegex(List bankNameRegex) {
        this.bankNameRegex = bankNameRegex;
    }

    public List getTransactionTypeRegex() {
        return transactionTypeRegex;
    }

    public void setTransactionTypeRegex(List transactionTypeRegex) {
        this.transactionTypeRegex = transactionTypeRegex;
    }

    public List getMerchantRegex() {
        return merchantRegex;
    }

    public void setMerchantRegex(List merchantRegex) {
        this.merchantRegex = merchantRegex;
    }

    public List getScamRegex() {
        return scamRegex;
    }

    public void setScamRegex(List scamRegex) {
        this.scamRegex = scamRegex;
    }
}

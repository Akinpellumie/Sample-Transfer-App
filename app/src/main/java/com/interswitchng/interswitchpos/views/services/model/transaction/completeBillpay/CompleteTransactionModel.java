package com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay;

public class CompleteTransactionModel {
    public int status;
    public String message;
    public Data data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}

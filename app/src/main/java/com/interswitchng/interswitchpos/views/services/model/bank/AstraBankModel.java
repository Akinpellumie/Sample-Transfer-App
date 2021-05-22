package com.interswitchng.interswitchpos.views.services.model.bank;

import java.util.List;

public class AstraBankModel {
    public int status;
    public String message;
    public List<BankData> data;

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

    public List<BankData> getData() {
        return data;
    }

    public void setData(List<BankData> data) {
        this.data = data;
    }
}

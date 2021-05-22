package com.interswitchng.interswitchpos.views.services.callback;

import com.interswitchng.interswitchpos.views.services.model.bank.BankData;

public interface IBankCallbackListener {
    void onDataReceived(BankData banks);
}
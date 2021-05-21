package com.interswitchng.interswitchpos.views.services.callback;

import com.interswitchng.interswitchpos.views.services.model.bank.AstraBankModel;
import com.interswitchng.interswitchpos.views.services.model.bank.BankData;
import com.interswitchng.interswitchpos.views.services.model.transactionrecord.TransactionRecord;

public interface IBankListServiceCallback {
    public void getBankList(AstraBankModel banks);
    void onDataReceived(BankData banks);
}

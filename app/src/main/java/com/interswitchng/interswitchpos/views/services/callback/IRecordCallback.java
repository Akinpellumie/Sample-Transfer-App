package com.interswitchng.interswitchpos.views.services.callback;

import com.interswitchng.interswitchpos.views.services.model.transactionrecord.TransactionRecord;

public interface IRecordCallback {
    public void getTransactions(TransactionRecord record);
}

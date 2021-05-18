package com.interswitchng.interswitchpos.views.services.interfaces;

import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel;
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel;

public interface ISendCashTransferCallBack {
    public void OnSendCashInitialize(TranxnInitiateModel tranxnInitiateData);
    public void OnSendCashComplete(CompleteTransactionModel commpleteTranxnData);
}

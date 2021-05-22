package com.interswitchng.interswitchpos.views.services.interfaces;

import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel;
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel;

public interface ICableTvPayCallBack {
    public void OnCableTvPayInitialize(TranxnInitiateModel tranxnInitiateData);
    public void OnCableTvPayComplete(CompleteTransactionModel commpleteTranxnData);
}

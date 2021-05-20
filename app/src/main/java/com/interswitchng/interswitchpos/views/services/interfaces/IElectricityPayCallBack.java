package com.interswitchng.interswitchpos.views.services.interfaces;

import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel;
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel;

public interface IElectricityPayCallBack {
    public void OnElectricityPayInitialize(TranxnInitiateModel tranxnInitiateData);
    public void OnElectricityPayComplete(CompleteTransactionModel commpleteTranxnData);
}

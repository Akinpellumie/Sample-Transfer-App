package com.interswitchng.interswitchpos.views.services.interfaces;

import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel;

public interface ICompleteBillCallBack {
    public void OnCompleteBillPayTranxn(CompleteTransactionModel commpleteTranxnData);
}

package com.interswitchng.interswitchpos.views.services.interfaces;

import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel;

public interface IAirtimeInitiateCallBack {
    public void OnAirtimeInitiate(TranxnInitiateModel tranxnInitiateData);
}

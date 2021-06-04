package com.interswitchng.interswitchpos.views.services.request;

import android.os.AsyncTask;

import com.interswitchng.interswitchpos.views.services.callback.IGetTerminalInfoCallBack;

public class GetTerminalDetails extends AsyncTask<String, Void, String> {
    private final IGetTerminalInfoCallBack callBack;

    public GetTerminalDetails(IGetTerminalInfoCallBack callBack) {
        this.callBack = callBack;
    }

//    public String getTerminalSerialNumber(){
//        public static String getSerial();
//    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}

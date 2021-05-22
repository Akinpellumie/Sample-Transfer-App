package com.interswitchng.interswitchpos.views.services.request;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.interswitchng.interswitchpos.views.services.Constants;
import com.interswitchng.interswitchpos.views.services.callback.IBankDetailCallBack;
import com.interswitchng.interswitchpos.views.services.callback.IFlowCallBack;
import com.interswitchng.interswitchpos.views.services.model.bank.AccountDetailModel;
import com.interswitchng.interswitchpos.views.services.model.home.FlowModel;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPhoneNumber;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPin;

public class VerifyAccountDetailService extends AsyncTask<String, Void, AccountDetailModel> {
    private final IBankDetailCallBack bankDetailCallBack;
    private String agentId;
    String userPhne = loggedInAgentPhoneNumber;
    String userPin = loggedInAgentPin;

    public VerifyAccountDetailService(IBankDetailCallBack bankDetailCallBack){
        this.bankDetailCallBack = bankDetailCallBack;
    }

    public AccountDetailModel getAccountName(String bankCode, String acctNumber){
        try{
            String url = Constants.VerifyAccountDetailUrl()+ bankCode + "/" + acctNumber;

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
//            MediaType mediaType = MediaType.parse("application/json");
//            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .addHeader("longitude", "3.142")
                    .addHeader("latitude", "-0.98")
                    .addHeader("corrlation-id", "1023")
                    .addHeader("Pin", userPin)
                    .addHeader("Phone", userPhne)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Gson gson = new Gson();

//            TransactionRecord tr = gson.fromJson(jsonString, TransactionRecord.class);
//            return tr;
            return gson.fromJson(jsonString, AccountDetailModel.class);
        }
        catch (Exception ex){
            Log.d("NotifyException::::", ex.getMessage());
        }
        return null;
    }

    @Override
    protected AccountDetailModel doInBackground(String... strings) {
        String bankCode = strings[0];
        String acctNumber = strings[1];
        return getAccountName(bankCode,acctNumber);
    }

    @Override
    protected void onPostExecute(AccountDetailModel accountDetailModel) {
        bankDetailCallBack.getUserAccountName(accountDetailModel);

        //super.onPostExecute(transactionRecordService);
    }
}

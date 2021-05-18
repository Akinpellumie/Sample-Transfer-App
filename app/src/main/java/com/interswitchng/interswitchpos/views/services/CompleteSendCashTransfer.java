package com.interswitchng.interswitchpos.views.services;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.interswitchng.interswitchpos.views.services.interfaces.ICompleteBillCallBack;
import com.interswitchng.interswitchpos.views.services.interfaces.ISendCashTransferCallBack;
import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentId;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPhoneNumber;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPin;

public class CompleteSendCashTransfer extends AsyncTask<String, Void, CompleteTransactionModel> {

    private final ISendCashTransferCallBack callBack;

    public CompleteSendCashTransfer(ISendCashTransferCallBack callBack) {
        this.callBack = callBack;
    }

    public CompleteTransactionModel completeBillPay(String transactionId, String pin){
        String url = Constants.CompleteTransactionUrl();


        try{

            String tranxnId = transactionId;
            String userPhne = loggedInAgentPhoneNumber;
            String userPin = pin;
            OkHttpClient okHttp = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject fullData = new JSONObject();

            try{
                JSONObject interswitchRef = new JSONObject();


                interswitchRef.put("interswitchRef", " ");
                //add all json data to fulldata
                fullData.put("interswitchDetails", interswitchRef);
                fullData.put("status", "");
                fullData.put("charge", "");
                fullData.put("transactionId", tranxnId);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            String json = fullData.toString();
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("longitude", "3.142")
                    .addHeader("latitude", "-0.98")
                    .addHeader("corrlation-id", "1023")
                    .addHeader("Pin", userPin)
                    .addHeader("Phone", userPhne)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            int statusCode = response.code();
            Gson gson = new Gson();

            //LoginModel user = new LoginModel();
            return gson.fromJson(responseBody, CompleteTransactionModel.class);


        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }
    @Override
    protected CompleteTransactionModel doInBackground(String... strings) {
        String transactionId = strings[0];
        String pin = strings[1];

        return completeBillPay(transactionId, pin);
    }

    @Override
    protected void onPostExecute(CompleteTransactionModel completeTranxnData) {
        callBack.OnSendCashComplete(completeTranxnData);
    }
}

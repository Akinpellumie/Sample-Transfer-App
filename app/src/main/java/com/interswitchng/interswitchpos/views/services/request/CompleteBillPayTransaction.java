package com.interswitchng.interswitchpos.views.services.request;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.interswitchng.interswitchpos.views.services.Constants;
import com.interswitchng.interswitchpos.views.services.interfaces.IAirtimeInitiateCallBack;
import com.interswitchng.interswitchpos.views.services.interfaces.ICompleteBillCallBack;
import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPhoneNumber;

public class CompleteBillPayTransaction extends AsyncTask<String, Void, CompleteTransactionModel> {

    private final ICompleteBillCallBack callBack;

    public CompleteBillPayTransaction(ICompleteBillCallBack callBack) {
        this.callBack = callBack;
    }

    public CompleteTransactionModel completeBillPay(String transactionId, String pin){
        String url = Constants.CompleteTransactionUrl();


        try{

            String userPhne = loggedInAgentPhoneNumber;
            String tranxnId = transactionId;
            OkHttpClient okHttp = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject fullData = new JSONObject();

            try{
                JSONObject interswitchDetails = new JSONObject();


                interswitchDetails.put("interswitchRef", " ");
                //add all json data to fulldata
                fullData.put("interswitchDetails", interswitchDetails);
                fullData.put("status", "");
                fullData.put("charge", "");
                fullData.put("transactionId", tranxnId);
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, fullData.toString());
            Request newReq = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("longitude", "3.142")
                    .addHeader("latitude", "-0.98")
                    .addHeader("corrlation-id", "1023")
                    .addHeader("Pin", pin)
                    .addHeader("Phone", userPhne)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = okHttp.newCall(newReq).execute();
            String respBody= response.body().string();
            int statusCode = response.code();
            Gson gson = new Gson();

            //LoginModel user = new LoginModel();
            return gson.fromJson(respBody, CompleteTransactionModel.class);

            //return gson.fromJson(res, LoginModel.class);

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
        callBack.OnCompleteBillPayTranxn(completeTranxnData);
    }
}

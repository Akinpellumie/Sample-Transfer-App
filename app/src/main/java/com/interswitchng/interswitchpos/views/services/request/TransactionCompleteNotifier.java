package com.interswitchng.interswitchpos.views.services.request;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.interswitchng.interswitchpos.views.services.Constants;
import com.interswitchng.interswitchpos.views.services.model.login.LoginModel;
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentEmail;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPhoneNumber;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPin;

public class TransactionCompleteNotifier extends AsyncTask<String, Void, Void> {

    LoginModel userData = new LoginModel();

    public void sendTransactionStatusToAstra(String status, String ref){
        try{
            String userPhne = loggedInAgentPhoneNumber;
            String userPin = loggedInAgentPin;
            String transId = Constants.TransId;
            JSONObject statusData = new JSONObject();

//            try{
                JSONObject interswitchDetails = new JSONObject();

                //add interswitchDetails details
            interswitchDetails.put( "interswitchRef",ref);

                //add all json data to statusData
            statusData.put("transactionId", transId);
            statusData.put("charge", 0);
            statusData.put("status", status);
            statusData.put("interswitchDetails", interswitchDetails);

//
//            }
//            catch (JSONException e){
//                e.printStackTrace();
//            }

            String url = Constants.CompleteTransactionUrl();
            String json = statusData.toString();
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
            String initResponse = response.body().string();

            //JSONObject userObject = new JSONObject(res);
            Gson gson = new Gson();

//            TranxnInitiateModel transRes = gson.fromJson(initResponse, TranxnInitiateModel.class);
//            String transId = transRes.getData().transactionId;
////            //LoginModel user = new LoginModel();
//            return
            //
        }
        catch (Exception ex){
            Log.d("NotifyException::::", ex.getMessage());
        }

    }

    @Override
    protected Void doInBackground(String... strings) {

        String status = strings[0];
       // String status = strings[1];
        String ref = strings[1];
//        String cardPan = strings[3];
        sendTransactionStatusToAstra(status, ref);
        return null;
    }
}


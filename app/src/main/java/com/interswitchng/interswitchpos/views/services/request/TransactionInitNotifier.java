package com.interswitchng.interswitchpos.views.services.request;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.interswitchng.interswitchpos.views.services.Constants;
import com.interswitchng.interswitchpos.views.services.model.login.LoginModel;
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentEmail;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentId;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPhoneNumber;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPin;

public class TransactionInitNotifier extends AsyncTask<String, Void, Void> {

    LoginModel userData = new LoginModel();

    public void notifyAstra(String amount, String cardType){
        try{
            String email = loggedInAgentEmail;
            String userId = loggedInAgentId;
            String userPhne = loggedInAgentPhoneNumber;
            String userPin = loggedInAgentPin;
            JSONObject fullData = new JSONObject();

//            try{
                JSONObject terminalDetails = new JSONObject();
                JSONObject facilitator = new JSONObject();
                JSONObject beneficiary = new JSONObject();
                JSONObject beneficiaryTerminal = new JSONObject();
                JSONObject cardDetails = new JSONObject();

                //add card details
                cardDetails.put( "pan"," ");
                cardDetails.put( "cardType",cardType);


                //add terminal details
                terminalDetails.put( "id","6fd02997-ac0f-4926-8191-194625310971");
                terminalDetails.put( "name","AST-09829");
                terminalDetails.put( "mac","ghdido:f0e83ud");
                terminalDetails.put( "long","1.03");
                terminalDetails.put( "lat","9.08");

                //add facilitator details

                facilitator.put( "id",userId);
                facilitator.put( "phone",userPhne);
                facilitator.put( "pin",userPin);
                facilitator.put(  "email",email);

                //add beneficiary
                beneficiary.put(   "email", " ");
                beneficiary.put(   "name", " ");
                beneficiary.put(   "phone", " ");

                //add beneficiaryTerminal details
                beneficiaryTerminal.put(   "billerName"," ");
                beneficiaryTerminal.put(   "billerId"," ");
                beneficiaryTerminal.put(    "categoryId"," ");
                beneficiaryTerminal.put(    "categoryName","CARD WITHDRAWAL");
                beneficiaryTerminal.put(    "paymentCode"," ");
                beneficiaryTerminal.put(    "paymentItemName"," ");
                beneficiaryTerminal.put(    "customerId"," ");

                //add all json data to fulldata
                fullData.put("terminalDetails", terminalDetails);
                fullData.put("facilitator", facilitator);
                fullData.put("beneficiary", beneficiary);
                fullData.put("beneficiaryTerminal", beneficiaryTerminal);
                fullData.put("cardDetails", cardDetails);
                fullData.put("amount", amount);
                fullData.put("narration", "withdraw money from card");
                fullData.put("transactionType","CARDWITHDRAWAL");
//
//            }
//            catch (JSONException e){
//                e.printStackTrace();
//            }

            String url = "http://192.168.3.169:3333/pin/transactions/initiate";
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
            String initResponse = response.body().string();

            //JSONObject userObject = new JSONObject(res);
            Gson gson = new Gson();

            TranxnInitiateModel transRes = gson.fromJson(initResponse, TranxnInitiateModel.class);
            String transId = transRes.getData().transactionId;
            Constants.TransId = transId;
//            //LoginModel user = new LoginModel();
//            return
            //
        }
        catch (Exception ex){
            Log.d("NotifyException::::", ex.getMessage());
        }

    }

    @Override
    protected Void doInBackground(String... strings) {

        String amount = strings[0];
       // String status = strings[1];
        String cardType = strings[1];
//        String cardPan = strings[3];
        notifyAstra(amount, cardType);
        return null;
    }
}


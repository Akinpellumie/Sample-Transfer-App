package com.interswitchng.interswitchpos.views.services;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TransactionStatusNotifier extends AsyncTask<String, Void, Void> {


    public void notifyAstra(String amount, String status, String cardType){
        try{

            JSONObject fullData = new JSONObject();

            try{
                JSONObject terminalDetails = new JSONObject();
                JSONObject facilitator = new JSONObject();
                JSONObject beneficiary = new JSONObject();
                JSONObject beneficiaryTerminal = new JSONObject();
                JSONObject cardDetails = new JSONObject();

                //add card details
                cardDetails.put( "pan"," ");
                cardDetails.put( "cardType"," ");


                //add terminal details
                terminalDetails.put( "id","6fd02997-ac0f-4926-8191-194625310971");
                terminalDetails.put( "name","AST-09829");
                terminalDetails.put( "mac","ghdido:f0e83ud");
                terminalDetails.put( "long","1.03");
                terminalDetails.put( "lat","9.08");

                //add facilitator details

                facilitator.put( "id","0d7d986d-c738-463c-865a-b09650c9bd07");
                facilitator.put( "phone","09057127737");
                facilitator.put( "pin","1234");
                facilitator.put(  "email","tosin@gmail.com");

                //add beneficiary
                beneficiary.put(   "email", "titanskayar@gmail.com");
                beneficiary.put(   "name", "Femi Williams");
                beneficiary.put(   "phone", "08122478910");

                //add beneficiaryTerminal details
                beneficiaryTerminal.put(   "billerName","DSTV");
                beneficiaryTerminal.put(   "billerId","1234");
                beneficiaryTerminal.put(    "categoryId","567");
                beneficiaryTerminal.put(    "categoryName","Cable TV Bills");
                beneficiaryTerminal.put(    "paymentCode","011");
                beneficiaryTerminal.put(    "paymentItemName","DSTV Compact");
                beneficiaryTerminal.put(    "customerId","08109827654");

                //add all json data to fulldata
                fullData.put("terminalDetails", terminalDetails);
                fullData.put("facilitator", facilitator);
                fullData.put("beneficiary", beneficiary);
                fullData.put("beneficiaryTerminal", beneficiaryTerminal);
                fullData.put("cardDetails", cardDetails);
                fullData.put("amount", amount);
                fullData.put("transactionType","BILLPAYMENTWITHCASH");

            }
            catch (JSONException e){
                e.printStackTrace();
            }

            String url = "http://192.168.3.169:3333/transactions";
            String json =  "{\r\n  \"amount\": "+amount
                    +",\r\n  \"status\" : \""+status
                    +"\",\r\n  \"card_type\" : \""+cardType+"\"\r\n}";
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            response = response;

        }
        catch (Exception ex){
            Log.d("NotifyException::::", ex.getMessage());
        }

    }

    @Override
    protected Void doInBackground(String... strings) {

        String amount = strings[0];
        String status = strings[1];
        String cardType = strings[2];
        notifyAstra(amount, status, cardType);
        return null;
    }
}


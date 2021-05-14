package com.interswitchng.interswitchpos.views.services;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TransactionStatusNotifier extends AsyncTask<String, Void, Void> {


    public void notifyAstra(String amount, String status, String cardType){
        try{
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


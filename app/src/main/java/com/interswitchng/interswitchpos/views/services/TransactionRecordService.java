package com.interswitchng.interswitchpos.views.services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransactionRecordService extends AsyncTask<String, Void, TransactionRecordService> {
    private RecordCallback callback;
    private String agentId;

    public TransactionRecordService(RecordCallback callback, String agentId){
        this.callback = callback;
        this.agentId = agentId;
    }

    public TransactionRecordService getTransactionRecord(){
        try{
            String url = "http://192.168.3.169:3333/agent/transactions/2d9eca54-0a68-473a-9c4b-d3d2cb5f9b9b";

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");

            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            response = response;

            String jsonString = response.body().string();
            Gson gson = new Gson();
            TransactionRecordService tr = gson.fromJson(jsonString, TransactionRecordService.class);
            return tr;
        }
        catch (Exception ex){
            Log.d("NotifyException::::", ex.getMessage());
        }
        return null;
    }

    @Override
    protected TransactionRecordService doInBackground(String... strings) {
        return getTransactionRecord();
    }

    @Override
    protected void onPostExecute(TransactionRecordService transactionRecordService) {
        super.onPostExecute(transactionRecordService);
    }
}

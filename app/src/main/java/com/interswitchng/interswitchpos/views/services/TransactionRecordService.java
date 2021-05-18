package com.interswitchng.interswitchpos.views.services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.interswitchng.interswitchpos.views.services.callback.IRecordCallback;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPhoneNumber;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPin;

public class TransactionRecordService extends AsyncTask<String, Void, TransactionRecordService> {
    private IRecordCallback callback;
    private String agentId;
    String userPhne = loggedInAgentPhoneNumber;
    String userPin = loggedInAgentPin;

    public TransactionRecordService(IRecordCallback callback){
        this.callback = callback;
    }

    public TransactionRecordService getTransactionRecord(){
        try{
            String url = Constants.TransactionHistoryUrl();

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

package com.interswitchng.interswitchpos.views.services.request;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.interswitchng.interswitchpos.views.services.Constants;
import com.interswitchng.interswitchpos.views.services.callback.IChangePinCallBack;
import com.interswitchng.interswitchpos.views.services.callback.ISetPinCallBack;
import com.interswitchng.interswitchpos.views.services.model.pin.PinModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPhoneNumber;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPin;

public class SetPinRequest extends AsyncTask<String, Void, PinModel> {

    private final ISetPinCallBack callBack;

    public SetPinRequest(ISetPinCallBack callBack) {
        this.callBack = callBack;
    }

    public PinModel setPinExecute(String requestId, String newPin){
        String url = Constants.setPinUrl();


        try{

            String userPhne = loggedInAgentPhoneNumber;
            String userPin = loggedInAgentPin;
            OkHttpClient okHttp = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject fullData = new JSONObject();

            try{
                //add all json data to fulldata
                fullData.put("requestId", requestId);
                fullData.put("newPin", newPin);
                fullData.put("agentPhone", userPhne);
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

            if(statusCode==500){
                return null;
            }
            //LoginModel user = new LoginModel();
            return gson.fromJson(responseBody, PinModel.class);


        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }
    @Override
    protected PinModel doInBackground(String... strings) {
        String requestId = strings[0];
        String newPin = strings[1];

        return setPinExecute(requestId, newPin);
    }

    @Override
    protected void onPostExecute(PinModel pinModel) {
        callBack.getSetPinResponse(pinModel);
    }
}

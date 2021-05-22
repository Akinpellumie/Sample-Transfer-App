package com.interswitchng.interswitchpos.views.services.request;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.interswitchng.interswitchpos.views.services.Constants;
import com.interswitchng.interswitchpos.views.services.interfaces.ILoginCallBack;
import com.interswitchng.interswitchpos.views.services.model.login.LoginModel;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginService extends AsyncTask<String, Void, LoginModel> {

    private final ILoginCallBack callBack;

    public LoginService(ILoginCallBack callBack){
        this.callBack = callBack;
    }


    public LoginModel LoginAsyncTask(String userId, String userPin){
        try{
            String url = Constants.LoginUrl();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",userId);
            jsonObject.put("password",userPin);
            String json = jsonObject.toString();

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
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            String res = response.body().string();

            //JSONObject userObject = new JSONObject(res);
            Gson gson = new Gson();

            //LoginModel user = new LoginModel();
            return gson.fromJson(res, LoginModel.class);

            //return gson.fromJson(res, LoginModel.class);

        }
        catch (Exception ex){
            Log.d("NotifyException::::", ex.getMessage());
        }
        return null;
    }

    @Override
    protected LoginModel doInBackground(String... strings) {

        String userId = strings[0];
        String userPin = strings[1];
        return LoginAsyncTask(userId, userPin);
    }

    @Override
    protected void onPostExecute(LoginModel user) {
        if(user==null){
            Constants.loggedInAgentEmail = "";
            Constants.loggedInAgentId = "";
        }
        else if(user.getStatus()==200){
            Constants.loggedInAgentEmail = user.getData().getProfileInfo().email;
            Constants.loggedInAgentId = user.getData().id;
        }
        else{
            Constants.loggedInAgentEmail = "";
            Constants.loggedInAgentId = "";
        }
        callBack.OnLogin(user);
    }
}

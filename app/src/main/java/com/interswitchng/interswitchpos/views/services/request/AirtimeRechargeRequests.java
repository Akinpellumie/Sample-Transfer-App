package com.interswitchng.interswitchpos.views.services.request;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.interswitchng.interswitchpos.views.services.Constants;
import com.interswitchng.interswitchpos.views.services.interfaces.IAirtimeInitiateCallBack;
import com.interswitchng.interswitchpos.views.services.interfaces.ILoginCallBack;
import com.interswitchng.interswitchpos.views.services.model.login.LoginModel;
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentEmail;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentId;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPhoneNumber;
import static com.interswitchng.interswitchpos.views.services.Constants.loggedInAgentPin;

public class AirtimeRechargeRequests extends AsyncTask<String, Void, TranxnInitiateModel> {

    private final IAirtimeInitiateCallBack callBack;

    public AirtimeRechargeRequests(IAirtimeInitiateCallBack callBack) {
        this.callBack = callBack;
    }

    public TranxnInitiateModel postRechargeWithCash(String phoneNumber, String amount, String rechargeType){
        String url = Constants.InititateTransactionUrl();


        try{

            String billerId = "";
            String paymentCode = "";
            if(rechargeType == "Mtn"){
                paymentCode = Constants.getMtnRechargeCustomPaycode();
                billerId = Constants.getMtnVtuBiller();
            } else if (rechargeType.contains("9Mobile")) {
                paymentCode = Constants.getEtisalatRechargeCustomPaycode();
                billerId = Constants.get9MobileVtuBiller();
            }else if (rechargeType.contains("Airtel")){
                paymentCode = Constants.getAirtelRechargeCustomPaycode();
                billerId = Constants.getAirtelVtuBiller();
            }else if (rechargeType.contains("Glo")){
                paymentCode = Constants.getGloRechargeCustomPaycode();
                billerId = Constants.getGloVtuBiller();
            }

            int amountInt = Integer.parseInt(amount);
            OkHttpClient okHttp = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject fullData = new JSONObject();

            try{
                String email = loggedInAgentEmail;
                String userPhne = loggedInAgentPhoneNumber;
                String userPin = loggedInAgentPin;
                String agentId = loggedInAgentId;

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

                facilitator.put( "id",agentId);
                facilitator.put( "phone",userPhne);
                facilitator.put( "pin",userPin);
                facilitator.put(  "email",email);

                //add beneficiary
                beneficiary.put(   "email", "");
                beneficiary.put(   "name", "");
                beneficiary.put(   "phone", phoneNumber);

                //add beneficiaryTerminal details
                beneficiaryTerminal.put(   "billerName",rechargeType);
                beneficiaryTerminal.put(   "billerId",billerId);
                beneficiaryTerminal.put(    "categoryId","567");
                beneficiaryTerminal.put(    "categoryName","Airtime Recharge");
                beneficiaryTerminal.put(    "paymentCode",paymentCode);
                beneficiaryTerminal.put(    "paymentItemName",rechargeType+" Recharge");
                beneficiaryTerminal.put(    "customerId",phoneNumber);

                //add all json data to fulldata
                fullData.put("terminalDetails", terminalDetails);
                fullData.put("facilitator", facilitator);
                fullData.put("beneficiary", beneficiary);
                fullData.put("beneficiaryTerminal", beneficiaryTerminal);
                fullData.put("cardDetails", cardDetails);
                fullData.put("amount", amountInt);
                fullData.put("transactionType","BILLPAYMENTWITHCASH");

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
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = okHttp.newCall(newReq).execute();
            String respBody= response.body().string();
            int statusCode = response.code();
            Gson gson = new Gson();

            //LoginModel user = new LoginModel();
            return gson.fromJson(respBody, TranxnInitiateModel.class);

            //return gson.fromJson(res, LoginModel.class);

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }
    @Override
    protected TranxnInitiateModel doInBackground(String... strings) {
        String phonenumber = strings[0];
        String amount = strings[1];
        String rechargeType = strings[2];
        return postRechargeWithCash(phonenumber, amount, rechargeType);

    }

    @Override
    protected void onPostExecute(TranxnInitiateModel tranxnInitiateData) {
        callBack.OnAirtimeInitiate(tranxnInitiateData);
    }
}

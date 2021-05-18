package com.interswitchng.interswitchpos.views.services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.interswitchng.interswitchpos.views.services.interfaces.ILoginCallBack;
import com.interswitchng.interswitchpos.views.services.interfaces.ISendCashTransferCallBack;
import com.interswitchng.interswitchpos.views.services.model.login.LoginModel;
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel;

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

public class SendCashTransferInitService extends AsyncTask<String, Void, TranxnInitiateModel> {

    LoginModel userData = new LoginModel();
    private final ISendCashTransferCallBack callBack;


    public SendCashTransferInitService(ISendCashTransferCallBack callBack) {
        this.callBack = callBack;
    }

    public TranxnInitiateModel initiateSendCash(String amount, String acctNum, String acctName, String bankName, String narration){
        try{
            String email = loggedInAgentEmail;
            String userPhne = loggedInAgentPhoneNumber;
            String userPin = loggedInAgentPin;
            String agentId = loggedInAgentId;
            String bankCode = " ";
            if(bankName.startsWith("First")){
                bankCode = Constants.getFBNcode();
            }
            else if(bankName.startsWith("Eco")){
                bankCode = Constants.getECOcode();
            }
            else if(bankName.startsWith("UBA")){
                bankCode = Constants.getUBAcode();
            }
            else if(bankName.startsWith("STANBIC")){
                bankCode = Constants.getStanbiccode();
            }
            else if(bankName.startsWith("GTBANK")){
                bankCode = Constants.getGTBcode();
            }
            JSONObject fullData = new JSONObject();

//            try{
                JSONObject terminalDetails = new JSONObject();
                JSONObject facilitator = new JSONObject();
                JSONObject beneficiary = new JSONObject();
                JSONObject beneficiaryTerminal = new JSONObject();
                JSONObject cardDetails = new JSONObject();

                //add card details
                cardDetails.put( "pan"," ");
                cardDetails.put( "cardType", " ");


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
                beneficiary.put(   "email", " ");
                beneficiary.put(   "name", acctName);
                beneficiary.put(   "phone", " ");

                //add beneficiaryTerminal details
                beneficiaryTerminal.put(   "billerName"," ");
                beneficiaryTerminal.put(   "teminalName",bankName);
                beneficiaryTerminal.put(   "accountNumber",acctNum);
                beneficiaryTerminal.put(   "accountName",acctName);
                beneficiaryTerminal.put(   "bankCode",bankCode);
                beneficiaryTerminal.put(   "bankId"," ");
                beneficiaryTerminal.put(   "bankName",bankName);
                beneficiaryTerminal.put(    "categoryId"," ");
                beneficiaryTerminal.put(    "categoryName"," ");
                beneficiaryTerminal.put(    "paymentCode"," ");
                beneficiaryTerminal.put(    "paymentItemName"," ");
                beneficiaryTerminal.put(    "customerId",acctNum);

                //add all json data to fulldata
                fullData.put("terminalDetails", terminalDetails);
                fullData.put("facilitator", facilitator);
                fullData.put("beneficiary", beneficiary);
                fullData.put("beneficiaryTerminal", beneficiaryTerminal);
                fullData.put("cardDetails", cardDetails);
                fullData.put("amount", amount);
                fullData.put("narration", narration);
                fullData.put("transactionType","CASHTRANSFER");
//
//            }
//            catch (JSONException e){
//                e.printStackTrace();
//            }

            String url = Constants.InititateTransactionUrl();
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
            Constants.SendCashInitTransId = transId;
//            //LoginModel user = new LoginModel();
//            return
            //
        }
        catch (Exception ex){
            Log.d("NotifyException::::", ex.getMessage());
        }
        return null;
    }

    @Override
    protected TranxnInitiateModel doInBackground(String... strings) {

        String amount = strings[0];
        String acctNum = strings[1];
        String acctName = strings[2];
        String bankName = strings[3];
        String narration = strings[4];
        return initiateSendCash(amount, acctNum,acctName,bankName,narration);
    }
    @Override
    protected void onPostExecute(TranxnInitiateModel data) {
        //Constants.SendCashInitTransId = data.getData().transactionId;
        callBack.OnSendCashInitialize(data);
    }
}


package com.interswitchng.interswitchpos.views.services;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.interswitchng.interswitchpos.views.services.model.cableplans.CableTvPlansModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CableTvDataLists {

    String jsonName = "";

    public ArrayList<CableTvPlansModel> getCableData(Context context, String cableTvType){
        try{
            // ArrayList for person names, email Id's and mobile numbers
            ArrayList<JSONObject> dstvPlans = new ArrayList<>();
            ArrayList<String> dstvPlanPaymentCode = new ArrayList<>();
            ArrayList<String> dstvPlanFee = new ArrayList<>();
            ArrayList<String> dstvPlanBillerId = new ArrayList<>();



            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, cableTvType));
            // fetch JSONArray named users
            JSONArray userArray = obj.getJSONArray(jsonName);

            //ArrayList<Phone> phones = YourDatabaseHelperClass.retrieveAllPhones();
            return fromJson(userArray);

//            for (int i = 0; i < userArray.length(); i++) {
//                JSONObject singlePlanDetails = new JSONObject();
//                // create a JSONObject for fetching single user data
//                JSONObject planDetail = userArray.getJSONObject(i);
//                // fetch email and name and store it in arraylist
////                dstvPlanBillerId.add(planDetail.getString("billerid"));
////                dstvPlanName.add(planDetail.getString("paymentitemname"));
////                dstvPlanPaymentCode.add(planDetail.getString("paymentCode"));
////                dstvPlanFee.add(planDetail.getString("itemFee"));
//
//                singlePlanDetails.put( "billerid",planDetail.getString("billerid"));
//                singlePlanDetails.put( "paymentitemname",planDetail.getString("paymentitemname"));
//                singlePlanDetails.put( "paymentCode",planDetail.getString("paymentitemname"));
//                singlePlanDetails.put( "itemFee",planDetail.getString("paymentCode"));
//
//                dstvPlans.add(singlePlanDetails);
//
//
//                //emailIds.add(userDetail.getString("email"));
//                // create a object for getting contact data from JSONObject
//                //JSONObject contact = planDetail.getJSONObject("contact");
//                // fetch mobile number and store it in arraylist
//                //mobileNumbers.add(contact.getString("mobile"));
//            }
//            return dstvPlans;

        }
        catch (Exception e){

        }
        return null;
    }

    public String loadJSONFromAsset(Context context, String cableTvType) {


        String json = null;
        try {
            AssetManager assManager = context.getAssets();
            InputStream is = null;
            if(cableTvType.matches("Dstv")){
                jsonName = "dstvPlans";
                is = assManager.open("dstvPackages.json");
            }
            else if (cableTvType.matches("Gotv")){
                is = assManager.open("gotvPackages.json");
                jsonName = "gotvPlans";
            }
            else if (cableTvType.matches("Startimes")){
                jsonName = "startimesPlans";
                is = assManager.open("startimesPackages.json");
            }

            //InputStream is = getApplicationContext().getAssets().open("dstvPackages.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // Decodes array of business json results into business model objects
    public ArrayList<CableTvPlansModel> fromJson(JSONArray jsonArray) {
        JSONObject cableTvJson;
        ArrayList<CableTvPlansModel> cableTvPlans = new ArrayList<CableTvPlansModel>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i=0; i < jsonArray.length(); i++) {
            try {
                cableTvJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            CableTvPlansModel c = cableTvDataJson(cableTvJson);
            if(c!=null){
                cableTvPlans.add(c);
            }

        }

        return cableTvPlans;
    }

    // Decodes plans json into plans model object
    public CableTvPlansModel cableTvDataJson(JSONObject jsonObject) {
        CableTvPlansModel c = new CableTvPlansModel();
        // Deserialize json into object fields
        try {
            c.billerid = jsonObject.getString("billerid");
            c.paymentitemname = jsonObject.getString("paymentitemname");
            c.paymentCode = jsonObject.getString("paymentCode");
            c.itemFee = jsonObject.getString("itemFee");
            c.amount = jsonObject.getString("amount");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return c;
    }

}

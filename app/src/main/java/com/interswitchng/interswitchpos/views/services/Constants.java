package com.interswitchng.interswitchpos.views.services;

import com.interswitchng.interswitchpos.views.services.model.bank.BankData;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static String domainurl()
    {
        //return "http://192.168.3.169:3333";
        return "https://server.pos.astrapay.com.ng";
    }
//    public static String airtimeRechargeUrl() {
//        return Constants.domainurl() + "/pin/transactions/initiate";
//    }
    public static String InititateTransactionUrl() {
        return Constants.domainurl() + "/pin/transactions/initiate";
    }
    public static String CompleteTransactionUrl() {
        return Constants.domainurl() + "/transactions/complete";
    }
    public static String VerifyAccountDetailUrl() {
        return Constants.domainurl() + "/agent/account_detail/other_banks/";
    }
    public static String BankListUrl() {
        return Constants.domainurl() + "/agent/account_detail/bank_codes";
    }
    public static String TransactionHistoryUrl() {
        return Constants.domainurl() + "/agent/transactions/agents/" + loggedInAgentId;
    }
    public static String FetchAgentFlowUrl() {
        return Constants.domainurl() + "/agent/transaction/flow/" + loggedInAgentId;
    }
    public static String FetchSingleTransactionUrl() {
        return Constants.domainurl() + "/agent/transactions/" + loggedInAgentId;
    }
    public static String LoginUrl() {
        return Constants.domainurl() + "/pin/login";
    }
    public static String getRechargeCustomPaycode() {
        return "234590";
    }

    public static String getMtnRechargeCustomPaycode() {
        return "10906";
    }

    public static String getGloRechargeCustomPaycode() {
        return "91309";
    }

    public static String getEtisalatRechargeCustomPaycode() {
        return "91309";
    }

    public static String getAirtelRechargeCustomPaycode() {
        return "90106";
    }

    public static String getBENINPayCode() {
        return "76701";
    }

    public static String getIBEDCPayCode() {
        return "78401";
    }

    public static String getEKODCPayCode() {
        return "24601";
    }

    public static String getPORTDCPayCode() {
        return "";
    }

    public static String getKADUNADCPayCode() {
        return "04296902";
    }

    public static String getIKEJADCPayCode() {
        return "76601";
    }

    public static String getDstvBillerId() {
        return "104";
    }
    public static ArrayList AST_BANK_LIST;
    public static String loggedInAgentPin;
    public static String loggedInAgentPhoneNumber;

    public static String loggedInAgentEmail;
    public static String loggedInAgentFirstname;
    public static String loggedInAgentId;
    public static String TransId;
    public static String SendCashInitTransId;
    public static String getFBNcode() {
        return "FBN";
    }
    public static String getECOcode() {
        return "ECO";
    }
    public static String getGTBcode() {
        return "GTB";
    }
    public static String getUBAcode() {
        return "UBA";
    }
    public static String getZIBcode() {
        return "ZIB";
    }
    public static String getABPcode() {
        return "ABP";
    }
    public static String getStanbiccode() {
        return "STANBIC";
    }

    public static String getMtnVtuBiller() {
        return "109";
    }
    public static String getGloVtuBiller() {
        return "402";
    }
    public static String getAirtelVtuBiller() {
        return "901";
    }
    public static String get9MobileVtuBiller() {
        return "120";
    }
    public static String getDstvPlanBiller() {
        return "104";
    }
    public static List<BankData> BANK_LIST = new ArrayList<BankData>();

}

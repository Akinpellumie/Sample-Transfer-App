package com.interswitchng.interswitchpos.views.services.model.transactionrecord;

import com.interswitchng.interswitchpos.views.services.model.login.Beneficiary;
import com.interswitchng.interswitchpos.views.services.model.login.BeneficiaryTerminal;

import java.util.Date;

public class Datum {
    public String id;
    public String flow;
    public int amount;
    public int charge;
    public String transactionType;
    public String status;
    public TerminalDetails terminalDetails;
    public PerformedBy performedBy;
    public CardHolderDetails cardHolderDetails;
    public InterswitchDetails interswitchDetails;
    public Beneficiary beneficiary;
    public BeneficiaryTerminal beneficiaryTerminal;
    public Date created_at;
    public String updated_at;
}

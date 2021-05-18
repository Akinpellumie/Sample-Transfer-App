package com.interswitchng.interswitchpos.views.services.model.transactionrecord;

import java.util.Date;

public class Datum {
    public String id;
    public int amount;
    public int charge;
    public String transactionType;
    public String status;
    public TerminalDetails terminalDetails;
    public PerformedBy performedBy;
    public CardHolderDetails cardHolderDetails;
    public InterswitchDetails interswitchDetails;
    public Date created_at;
    public String updated_at;
}

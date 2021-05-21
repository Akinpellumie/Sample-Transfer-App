package com.interswitchng.interswitchpos.utils.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.interswitchng.interswitchpos.R;
import com.interswitchng.interswitchpos.views.services.callback.ISingleTransactionSelectionCallBack;
import com.interswitchng.interswitchpos.views.services.model.transactionrecord.Datum;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TransactionsRecyclerAdapter extends RecyclerView.Adapter<TransactionsRecyclerAdapter.ViewHolder>{
    private List<Datum> localDataSet;
    //Datum currentitem;
    private ISingleTransactionSelectionCallBack issCallBack;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
//    public TransactionsRecyclerAdapter(List<Datum> dataSet) {
//        localDataSet = dataSet;
//    }

    public TransactionsRecyclerAdapter(List<Datum> dataSet, ISingleTransactionSelectionCallBack issCallBack) {
        localDataSet = dataSet;
        this.issCallBack = issCallBack;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recent_card_record, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TransactionsRecyclerAdapter.ViewHolder holder, int position) {
        Datum currentitem = localDataSet.get(position);
        //holder.imageView.setImageResource();
        holder.transactionTypeTextView.setText(formatTransactionType(currentitem.transactionType));
        holder.dateTextView.setText(formatTransactionDate(currentitem.created_at));
        holder.amountTextView.setText(formatTransactionAmount(currentitem));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issCallBack.onSelect(currentitem);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView transactionTypeTextView;
        private final TextView dateTextView;
        private final TextView amountTextView;
        private final View view;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            imageView = (ImageView) view.findViewById(R.id.iconView);
            transactionTypeTextView = (TextView) view.findViewById(R.id.desc);
            dateTextView = (TextView) view.findViewById(R.id.trans_date);
            amountTextView = (TextView) view.findViewById(R.id.transAmount);
            this.view = view;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTransactionTypeTextView() {
            return transactionTypeTextView;
        }

        public TextView getDateTextView() {
            return dateTextView;
        }

        public TextView getAmountTextView() {
            return amountTextView;
        }

        public View getView(){
            return view;
        }
    }

    private String formatTransactionType(String transType){

        if(transType.equalsIgnoreCase("BILLPAYMENTWITHCASH")){
            return "Bill Payment";
        }
        else  if(transType.equalsIgnoreCase("CASHTRANSFER")){
            return "Cash Transfer";
        }
        else  if(transType.equalsIgnoreCase("CARDWITHDRAWAL")){
            return "Card Withdrawal";
        }
        else{
            return transType;
        }
    }

    private String formatTransactionDate(Date transDate){
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd MMM, yyyy");
        String stringDate = DateFor.format(transDate);

        //return date.toString();
        return stringDate;
    }

    private String formatTransactionAmount(Datum item){
        double amount = item.amount;
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format("N %(,.2f", amount);
        if(item.flow.equalsIgnoreCase("debit")){

            return "-" + sb.toString();
        }else if(item.flow.equalsIgnoreCase("credit")){
            return "+" + sb.toString();
        }else{
            return sb.toString();
        }

    }
}

package com.interswitchng.interswitchpos.utils.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.interswitchng.interswitchpos.R;
import com.interswitchng.interswitchpos.views.services.model.transactionrecord.Datum;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TransactionsRecyclerAdapter extends RecyclerView.Adapter<TransactionsRecyclerAdapter.ViewHolder>{
    private List<Datum> localDataSet;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public TransactionsRecyclerAdapter(List<Datum> dataSet) {
        localDataSet = dataSet;
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
        holder.transactionTypeTextView.setText(currentitem.transactionType);
        holder.dateTextView.setText(currentitem.created_at);
        holder.amountTextView.setText(currentitem.amount);
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

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            imageView = (ImageView) view.findViewById(R.id.iconView);
            transactionTypeTextView = (TextView) view.findViewById(R.id.desc);
            dateTextView = (TextView) view.findViewById(R.id.trans_date);
            amountTextView = (TextView) view.findViewById(R.id.amount);
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
    }
}

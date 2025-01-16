package com.example.projetoprtico_controledefinanas;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List<Transaction> transactionList;
    private final OnTransactionLongClickListener longClickListener;

    public interface OnTransactionLongClickListener {
        void onTransactionLongClick(Transaction transaction);
    }

    public TransactionAdapter(List<Transaction> transactionList, OnTransactionLongClickListener longClickListener) {
        this.transactionList = transactionList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.bind(transaction, longClickListener);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleText;
        private final TextView valueText;
        private final TextView categoryText;
        private final TextView dateText;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.transaction_title);
            valueText = itemView.findViewById(R.id.transaction_value);
            categoryText = itemView.findViewById(R.id.transaction_category);
            dateText = itemView.findViewById(R.id.transaction_date);
        }

        public void bind(Transaction transaction, OnTransactionLongClickListener longClickListener) {
            titleText.setText(transaction.getTitle());
            valueText.setText(String.format(Locale.getDefault(), "$%.2f", transaction.getValue()));
            categoryText.setText(transaction.getCategory());
            dateText.setText(dateFormat.format(transaction.getDate()));

            itemView.setOnLongClickListener(v -> {
                longClickListener.onTransactionLongClick(transaction);
                return true;
            });
        }
    }
}


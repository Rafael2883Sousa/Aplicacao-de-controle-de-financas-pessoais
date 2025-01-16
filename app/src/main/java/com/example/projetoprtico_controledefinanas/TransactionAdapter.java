package com.example.projetoprtico_controledefinanas;

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

    private final List<Transaction> transactionList; // Lista de transações
    private final OnTransactionLongClickListener longClickListener; // Listener para o clique longo nas transações

    // método para o clique longo na transação
    public interface OnTransactionLongClickListener {
        void onTransactionLongClick(Transaction transaction);
    }

    // Construtor do adapter, que recebe a lista de transações e o listener de clique longo
    public TransactionAdapter(List<Transaction> transactionList, OnTransactionLongClickListener longClickListener) {
        this.transactionList = transactionList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item da transação e cria o ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        // Obtém a transação da lista e vincula os dados ao ViewHolder
        Transaction transaction = transactionList.get(position);
        holder.bind(transaction, longClickListener);
    }

    @Override
    public int getItemCount() {
        // Retorna o tamanho da lista de transações
        return transactionList.size();
    }

    // Classe ViewHolder responsável por exibir os dados de uma transação
    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleText;
        private final TextView valueText;
        private final TextView categoryText; // Categoria da transação
        private final TextView dateText;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // Formato de data

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa as views do item
            titleText = itemView.findViewById(R.id.transaction_title);
            valueText = itemView.findViewById(R.id.transaction_value);
            categoryText = itemView.findViewById(R.id.transaction_category);
            dateText = itemView.findViewById(R.id.transaction_date);
        }

        // Vincula os dados da transação ao layout e configura o listener de clique longo
        public void bind(Transaction transaction, OnTransactionLongClickListener longClickListener) {
            // Preenche os campos com os dados da transação
            titleText.setText(transaction.getTitle());
            valueText.setText(String.format(Locale.getDefault(), "$%.2f", transaction.getValue())); // Exibe o valor formatado
            categoryText.setText(transaction.getCategory());
            dateText.setText(dateFormat.format(transaction.getDate())); // Exibe a data formatada

            // Configura o clique longo para a transação
            itemView.setOnLongClickListener(v -> {
                longClickListener.onTransactionLongClick(transaction); // Chama o método do listener
                return true; // Retorna true para indicar que o clique longo foi tratado
            });
        }
    }
}

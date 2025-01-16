package com.example.projetoprtico_controledefinanas;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class TransactionListActivity extends AppCompatActivity {

    private RecyclerView transactionRecyclerView;
    private TransactionAdapter transactionAdapter;
    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        transactionRecyclerView = findViewById(R.id.recycler_view_transactions);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.getAllTransactions().observe(this, this::initializeAdapter);
    }

    private void initializeAdapter(List<Transaction> transactions) {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactionAdapter = new TransactionAdapter(transactions, this::onTransactionLongClick);
        transactionRecyclerView.setAdapter(transactionAdapter);
    }


    private void onTransactionLongClick(Transaction transaction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.transaction_options)
                .setItems(new String[]{getString(R.string.edit), getString(R.string.delete)}, (dialog, which) -> {
                    if (which == 0) {
                        editTransaction(transaction);
                    } else if (which == 1) {
                        deleteTransaction(transaction);
                    }
                })
                .show();
    }

    private void editTransaction(Transaction transaction) {
        Intent intent = new Intent(TransactionListActivity.this, TransactionFormActivity.class);
        intent.putExtra("transaction_id", transaction.getId()); // Passa o ID da transação para identificar no banco de dados
        startActivity(intent);
    }

    private void deleteTransaction(Transaction transaction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm_deletion)
                .setMessage(R.string.deletion_message)  // Pede confirmação
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    transactionViewModel.delete(transaction);
                    transactionAdapter.notifyDataSetChanged();
                })
                .setNegativeButton(R.string.no, null)
                .show(); // Mostra um AlertDialog

    }
}
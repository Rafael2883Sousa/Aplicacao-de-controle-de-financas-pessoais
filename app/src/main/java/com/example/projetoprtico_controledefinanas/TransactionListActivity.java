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

    private RecyclerView transactionRecyclerView; // RecyclerView para exibir as transações
    private TransactionAdapter transactionAdapter;
    private TransactionViewModel transactionViewModel; // ViewModel para acessar os dados das transações

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        // Inicializa o RecyclerView e define o layout manager
        transactionRecyclerView = findViewById(R.id.recycler_view_transactions);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa o ViewModel para acessar as transações
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // chama o método para inicializar o adaptador
        transactionViewModel.getAllTransactions().observe(this, this::initializeAdapter);
    }

    private void initializeAdapter(List<Transaction> transactions) {
        // Se não houver transações, cria uma lista vazia
        if (transactions == null) {
            transactions = new ArrayList<>();
        }

        // Cria e define o adaptador do RecyclerView
        transactionAdapter = new TransactionAdapter(transactions, this::onTransactionLongClick);
        transactionRecyclerView.setAdapter(transactionAdapter);
    }

    private void onTransactionLongClick(Transaction transaction) {
        // Cria um diálogo com as opções de editar ou excluir
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.transaction_options)
                .setItems(new String[]{getString(R.string.edit), getString(R.string.delete)}, (dialog, which) -> {
                    if (which == 0) {
                        editTransaction(transaction); // Chama o método para editar
                    } else if (which == 1) {
                        deleteTransaction(transaction); // Chama o método para excluir
                    }
                })
                .show(); // Exibe o diálogo
    }

    private void editTransaction(Transaction transaction) {
        Intent intent = new Intent(TransactionListActivity.this, TransactionFormActivity.class);
        intent.putExtra("transaction_id", transaction.getId()); // Passa o ID da transação para identificar no banco de dados
        startActivity(intent);
    }


    private void deleteTransaction(Transaction transaction) {
        // Exibe um diálogo pedindo confirmação para deletar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm_deletion)
                .setMessage(R.string.deletion_message)  // Pede confirmação antes de deletar
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    transactionViewModel.delete(transaction); // Chama o método de deletar no ViewModel
                    transactionAdapter.notifyDataSetChanged(); // Notifica o adaptador para atualizar a lista
                })
                .setNegativeButton(R.string.no, null)
                .show(); // Exibe o diálogo
    }
}

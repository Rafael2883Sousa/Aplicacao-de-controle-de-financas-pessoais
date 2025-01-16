package com.example.projetoprtico_controledefinanas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView currentBalance, totalIncome, totalExpenses;
    private Button btnAddIncome, btnAddExpense, btnViewTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentBalance = findViewById(R.id.current_balance);
        totalIncome = findViewById(R.id.total_income);
        totalExpenses = findViewById(R.id.total_expenses);
        btnAddIncome = findViewById(R.id.btn_add_income);
        btnAddExpense = findViewById(R.id.btn_add_expense);
        btnViewTransactions = findViewById(R.id.btn_view_transactions);

        btnAddIncome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransactionFormActivity.class);
            intent.putExtra("type", "income"); // Envia o tipo de transação como extra
            startActivity(intent);
        });

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransactionFormActivity.class);
            intent.putExtra("type", "expense"); // Envia o tipo de transação como extra
            startActivity(intent);
        });

        btnViewTransactions.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransactionListActivity.class);
            startActivity(intent);
        });

        observeBalances(); // Atualizar os valores exibidos na interface
    }

    private void observeBalances() {
        TransactionViewModel transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        transactionViewModel.getAllTransactions().observe(this, transactions -> {
            double totalIncome = 0.0, totalExpenses = 0.0;
            for (Transaction transaction : transactions) {
                if (transaction.getValue() > 0) {
                    totalIncome += transaction.getValue();
                } else {
                    totalExpenses += transaction.getValue();
                }
            }

            currentBalance.setText(String.format(Locale.getDefault(), "$%.2f", totalIncome + totalExpenses));
            this.totalIncome.setText(String.format(Locale.getDefault(), "$%.2f", totalIncome));
            this.totalExpenses.setText(String.format(Locale.getDefault(), "$%.2f", Math.abs(totalExpenses)));
        });
    }
}

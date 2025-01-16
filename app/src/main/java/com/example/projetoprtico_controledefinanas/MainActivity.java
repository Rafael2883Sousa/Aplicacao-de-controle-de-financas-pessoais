package com.example.projetoprtico_controledefinanas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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

        btnAddIncome.setOnClickListener(v -> openTransactionForm("income"));
        btnAddExpense.setOnClickListener(v -> openTransactionForm("expense"));
        btnViewTransactions.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TransactionListActivity.class)));
    }

    private void openTransactionForm(String type) {
        Intent intent = new Intent(MainActivity.this, TransactionFormActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
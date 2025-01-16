package com.example.projetoprtico_controledefinanas;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

public class TransactionFormActivity extends AppCompatActivity {

    private EditText editTitle, editValue;
    private Spinner categorySpinner;
    private Button btnSave, btnCancel;
    private String transactionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_form);

        // Inicialização dos componentes
        editTitle = findViewById(R.id.edit_title);
        editValue = findViewById(R.id.edit_value);
        categorySpinner = findViewById(R.id.category_spinner);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(v -> saveTransaction());
        btnCancel.setOnClickListener(v -> finish());

        // Carregar a transação se for edição
        int transactionId = getIntent().getIntExtra("transaction_id", -1);
        if (transactionId != -1) {
            loadTransaction(transactionId);
        }
    }

    private void loadTransaction(int transactionId) {
        TransactionViewModel transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.getTransactionById(transactionId).observe(this, transaction -> {
            if (transaction != null) {
                editTitle.setText(transaction.getTitle());
                editValue.setText(String.valueOf(Math.abs(transaction.getValue())));
                // Configurar o spinner para a categoria existente
                int position = ((ArrayAdapter<String>) categorySpinner.getAdapter()).getPosition(transaction.getCategory());
                categorySpinner.setSelection(position);
            }
        });
    }



    private void saveTransaction() {
        String title = editTitle.getText().toString();
        String valueString = editValue.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();

        if (title.isEmpty() || valueString.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(valueString);
        if ("expense".equals(transactionType)) {
            value = -value;
        }

        Transaction transaction = new Transaction(title, value, category, new Date());

        int transactionId = getIntent().getIntExtra("transaction_id", -1);
        if (transactionId != -1) {
            transaction.setId(transactionId); // Atualizar a transação existente
        }

        TransactionViewModel transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.insert(transaction); // Inserir ou atualizar
        Toast.makeText(this, R.string.transaction_saved, Toast.LENGTH_SHORT).show();
        finish();
    }
}
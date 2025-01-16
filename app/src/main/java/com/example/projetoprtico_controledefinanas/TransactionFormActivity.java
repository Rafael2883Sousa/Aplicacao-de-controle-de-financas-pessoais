package com.example.projetoprtico_controledefinanas;

import android.os.Bundle;
import android.util.Log;
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
    private Spinner categorySpinner; // Spinner para selecionar a categoria da transação
    private Button btnSave, btnCancel;
    private String transactionType; // Tipo de transação (ingresso ou despesa)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_form);

        editTitle = findViewById(R.id.edit_title);
        editValue = findViewById(R.id.edit_value);
        categorySpinner = findViewById(R.id.category_spinner);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(v -> saveTransaction());
        btnCancel.setOnClickListener(v -> finish());

        // Carregar uma transação existente caso o ID seja passado
        int transactionId = getIntent().getIntExtra("transaction_id", -1);
        if (transactionId != -1) {
            loadTransaction(transactionId); // Carregar a transação a ser editada
        }
    }

    private void loadTransaction(int transactionId) {
        // Obtém o ViewModel e observa a transação com o ID específico
        TransactionViewModel transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.getTransactionById(transactionId).observe(this, transaction -> {
            if (transaction != null) {
                // Preenche os campos com os dados da transação
                editTitle.setText(transaction.getTitle());
                editValue.setText(String.valueOf(Math.abs(transaction.getValue())));
                // Configura o spinner para a categoria da transação
                int position = ((ArrayAdapter<String>) categorySpinner.getAdapter()).getPosition(transaction.getCategory());
                categorySpinner.setSelection(position); // Seleciona a categoria no spinner
            }
        });
    }

    private void saveTransaction() {
        String title = editTitle.getText().toString();
        String valueString = editValue.getText().toString();
        String category = categorySpinner.getSelectedItem().toString(); // Obtém a categoria selecionada

        // Verifica se os campos estão preenchidos
        if (title.isEmpty() || valueString.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
            return; // Interrompe se os campos estiverem vazios
        }

        double value;
        try {
            value = Double.parseDouble(valueString); // Converte o valor para double
        } catch (NumberFormatException e) {
            Log.e("TransactionForm", "Invalid value: " + valueString, e);
            Toast.makeText(this, "Invalid value format", Toast.LENGTH_SHORT).show();
            return; // Interrompe se o valor não for válido
        }

        // Se for uma despesa, o valor é negativo
        if ("expense".equals(transactionType)) {
            value = -value;
        }

        // Cria um objeto Transaction com os dados informados
        Transaction transaction = new Transaction(title, value, category, new Date());
        Log.d("TransactionForm", "Transaction: " + transaction);

        // Obtém o ID da transação para edição (caso já exista)
        int transactionId = getIntent().getIntExtra("transaction_id", -1);
        if (transactionId != -1) {
            transaction.setId(transactionId); // Atualiza a transação existente
        }

        // Obtém o ViewModel e insere ou atualiza a transação
        TransactionViewModel transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.insert(transaction); // Insere ou atualiza a transação no banco de dados
        Toast.makeText(this, R.string.transaction_saved, Toast.LENGTH_SHORT).show();
        finish(); // Finaliza a atividade
    }
}

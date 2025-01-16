package com.example.projetoprtico_controledefinanas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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

        editTitle = findViewById(R.id.edit_title);
        editValue = findViewById(R.id.edit_value);
        categorySpinner = findViewById(R.id.category_spinner);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        transactionType = getIntent().getStringExtra("type");

        btnSave.setOnClickListener(v -> saveTransaction());
        btnCancel.setOnClickListener(v -> finish());
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
        if (transactionType.equals("expense")) {
            value = -value;
        }

        Transaction transaction = new Transaction(title, value, category, new Date());
        // Save transaction to database (Room integration required)
        Toast.makeText(this, R.string.transaction_saved, Toast.LENGTH_SHORT).show();
        finish();
    }
}